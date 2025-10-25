package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_CLUBS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalClubs.ARCHERY;
import static seedu.address.testutil.TypicalClubs.BALL;
import static seedu.address.testutil.TypicalClubs.CHESS;
import static seedu.address.testutil.TypicalClubs.getTypicalAddressBook;
import static seedu.address.testutil.TypicalClubs.getTypicalClubs;

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
import seedu.address.model.club.Club;

/**
 * Contains integration tests (interaction with the Model) for {@code FindClubCommand}.
 */
public class FindClubCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        CombinedSearchPredicate<Club> firstPredicate = new CombinedSearchPredicate<>();
        CombinedSearchPredicate<Club> secondPredicate = new CombinedSearchPredicate<>();

        firstPredicate.add(new NameMatchesPredicate<>(Collections.singletonList("first")));
        secondPredicate.add(new NameMatchesPredicate<>(Collections.singletonList("second")));

        FindClubCommand findFirstCommand = new FindClubCommand(firstPredicate);
        FindClubCommand findSecondCommand = new FindClubCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same value -> returns true
        FindClubCommand findFirstCommandCopy = new FindClubCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        FindPersonCommand findPersonCommand = new FindPersonCommand(new CombinedSearchPredicate<>());
        FindClubCommand findClubCommand = new FindClubCommand(new CombinedSearchPredicate<>());
        assertFalse(findClubCommand.equals(findPersonCommand));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different club -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_noSearchParameters_allClubsFound() {
        String expectedMessage = String.format(MESSAGE_CLUBS_LISTED_OVERVIEW, 7);
        CombinedSearchPredicate<Club> predicate = new CombinedSearchPredicate<>();
        FindClubCommand command = new FindClubCommand(predicate);
        expectedModel.updateFilteredClubList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(getTypicalClubs(), model.getFilteredClubList());
    }

    @Test
    public void execute_zeroKeywords_noClubsFound() {
        String expectedMessage = String.format(MESSAGE_CLUBS_LISTED_OVERVIEW, 0);
        CombinedSearchPredicate<Club> predicate = new CombinedSearchPredicate<>();
        predicate.add(prepareNamePredicate(" "));
        FindClubCommand command = new FindClubCommand(predicate);
        expectedModel.updateFilteredClubList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredClubList());
    }

    @Test
    public void execute_multipleKeywords_multipleClubsFound() {
        String expectedMessage = String.format(MESSAGE_CLUBS_LISTED_OVERVIEW, 3);
        CombinedSearchPredicate<Club> predicate = new CombinedSearchPredicate<>();
        predicate.add(prepareNamePredicate("Archer Ball Chess"));
        FindClubCommand command = new FindClubCommand(predicate);
        expectedModel.updateFilteredClubList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ARCHERY, BALL, CHESS), model.getFilteredClubList());
    }

    @Test
    public void execute_multipleSearchParameters_multipleClubsFound() {
        String expectedMessage = String.format(MESSAGE_CLUBS_LISTED_OVERVIEW, 2);
        CombinedSearchPredicate<Club> predicate = new CombinedSearchPredicate<>();
        predicate.add(prepareNamePredicate("Archer Ball Chess"));
        predicate.add(prepareTagPredicate("sport"));
        FindClubCommand command = new FindClubCommand(predicate);
        expectedModel.updateFilteredClubList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ARCHERY, BALL), model.getFilteredClubList());
    }

    @Test
    public void execute_multipleSearchParameters_oneClubFound() {
        String expectedMessage = String.format(MESSAGE_CLUBS_LISTED_OVERVIEW, 1);
        CombinedSearchPredicate<Club> predicate = new CombinedSearchPredicate<>();
        predicate.add(prepareNamePredicate("Archer"));
        predicate.add(prepareTagPredicate("sport"));
        predicate.add(prepareAddressPredicate("Wes"));
        predicate.add(prepareEmailPredicate("archery"));
        predicate.add(preparePhonePredicate("1253"));
        FindClubCommand command = new FindClubCommand(predicate);
        expectedModel.updateFilteredClubList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.singletonList(ARCHERY), model.getFilteredClubList());
    }

    @Test
    public void toStringMethod() {
        CombinedSearchPredicate<Club> predicate = new CombinedSearchPredicate<>();
        predicate.add(new NameMatchesPredicate<>(Arrays.asList("keyword")));
        FindClubCommand findCommand = new FindClubCommand(predicate);
        String expected = FindClubCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, findCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code NameMatchesPredicate}.
     */
    private NameMatchesPredicate<Club> prepareNamePredicate(String userInput) {
        return new NameMatchesPredicate<>(Arrays.asList(userInput.split("\\s+")));
    }

    /**
     * Parses {@code userInput} into a {@code TagsMatchPredicate}.
     */
    private TagsMatchPredicate<Club> prepareTagPredicate(String userInput) {
        return new TagsMatchPredicate<>(Arrays.asList(userInput.split("\\s+")));
    }

    /**
     * Parses {@code userInput} into a {@code AddressMatchesPredicate}.
     */
    private AddressMatchesPredicate<Club> prepareAddressPredicate(String userInput) {
        return new AddressMatchesPredicate<>(Arrays.asList(userInput.split("\\s+")));
    }

    /**
     * Parses {@code userInput} into a {@code EmailMatchesPredicate}.
     */
    private EmailMatchesPredicate<Club> prepareEmailPredicate(String userInput) {
        return new EmailMatchesPredicate<>(Arrays.asList(userInput.split("\\s+")));
    }

    /**
     * Parses {@code userInput} into a {@code PhoneMatchesPredicate}.
     */
    private PhoneMatchesPredicate<Club> preparePhonePredicate(String userInput) {
        return new PhoneMatchesPredicate<>(Arrays.asList(userInput.split("\\s+")));
    }
}
