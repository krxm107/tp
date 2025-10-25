package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalPersons.getTypicalPersons;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.logic.search.CombinedSearchPredicate;
import seedu.address.logic.search.predicates.AddressMatchesPredicate;
import seedu.address.logic.search.predicates.EmailMatchesPredicate;
import seedu.address.logic.search.predicates.NameMatchesPredicate;
import seedu.address.logic.search.predicates.PhoneMatchesPredicate;
import seedu.address.logic.search.predicates.TagsMatchPredicate;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) for {@code FindPersonCommand}.
 */
public class FindPersonCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        CombinedSearchPredicate<Person> firstPredicate = new CombinedSearchPredicate<>();
        CombinedSearchPredicate<Person> secondPredicate = new CombinedSearchPredicate<>();

        firstPredicate.add(new NameMatchesPredicate<>(Collections.singletonList("first")));
        secondPredicate.add(new NameMatchesPredicate<>(Collections.singletonList("second")));

        FindPersonCommand findFirstCommand = new FindPersonCommand(firstPredicate);
        FindPersonCommand findSecondCommand = new FindPersonCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same value -> returns true
        FindPersonCommand findFirstCommandCopy = new FindPersonCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        FindPersonCommand findPersonCommand = new FindPersonCommand(new CombinedSearchPredicate<>());
        FindClubCommand findClubCommand = new FindClubCommand(new CombinedSearchPredicate<>());
        assertFalse(findPersonCommand.equals(findClubCommand));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_noSearchParameters_allPersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 7);
        CombinedSearchPredicate<Person> predicate = new CombinedSearchPredicate<>();
        FindPersonCommand command = new FindPersonCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(getTypicalPersons(), model.getFilteredPersonList());
    }

    @Test
    public void execute_zeroKeywords_noPersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        CombinedSearchPredicate<Person> predicate = new CombinedSearchPredicate<>();
        predicate.add(prepareNamePredicate(" "));
        FindPersonCommand command = new FindPersonCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        CombinedSearchPredicate<Person> predicate = new CombinedSearchPredicate<>();
        predicate.add(prepareNamePredicate("Paul Ben Kurz"));
        FindPersonCommand command = new FindPersonCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON, CARL), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleSearchParameters_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        CombinedSearchPredicate<Person> predicate = new CombinedSearchPredicate<>();
        predicate.add(prepareNamePredicate("Paul Ben Kurz"));
        predicate.add(prepareTagPredicate("friend"));
        FindPersonCommand command = new FindPersonCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleSearchParameters_onePersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        CombinedSearchPredicate<Person> predicate = new CombinedSearchPredicate<>();
        predicate.add(prepareNamePredicate("Paul"));
        predicate.add(prepareTagPredicate("friend"));
        predicate.add(prepareAddressPredicate("Juro"));
        predicate.add(prepareEmailPredicate("alice"));
        predicate.add(preparePhonePredicate("9435"));
        FindPersonCommand command = new FindPersonCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.singletonList(ALICE), model.getFilteredPersonList());
    }

    @Test
    public void toStringMethod() {
        CombinedSearchPredicate<Person> predicate = new CombinedSearchPredicate<>();
        predicate.add(new NameMatchesPredicate<>(Arrays.asList("keyword")));
        FindPersonCommand findCommand = new FindPersonCommand(predicate);
        String expected = FindPersonCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, findCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code NameMatchesPredicate}.
     */
    private NameMatchesPredicate<Person> prepareNamePredicate(String userInput) {
        return new NameMatchesPredicate<>(Arrays.asList(userInput.split("\\s+")));
    }

    /**
     * Parses {@code userInput} into a {@code TagsMatchPredicate}.
     */
    private TagsMatchPredicate<Person> prepareTagPredicate(String userInput) {
        return new TagsMatchPredicate<>(Arrays.asList(userInput.split("\\s+")));
    }

    /**
     * Parses {@code userInput} into a {@code AddressMatchesPredicate}.
     */
    private AddressMatchesPredicate<Person> prepareAddressPredicate(String userInput) {
        return new AddressMatchesPredicate<>(Arrays.asList(userInput.split("\\s+")));
    }

    /**
     * Parses {@code userInput} into a {@code EmailMatchesPredicate}.
     */
    private EmailMatchesPredicate<Person> prepareEmailPredicate(String userInput) {
        return new EmailMatchesPredicate<>(Arrays.asList(userInput.split("\\s+")));
    }

    /**
     * Parses {@code userInput} into a {@code PhoneMatchesPredicate}.
     */
    private PhoneMatchesPredicate<Person> preparePhonePredicate(String userInput) {
        return new PhoneMatchesPredicate<>(Arrays.asList(userInput.split("\\s+")));
    }
}
