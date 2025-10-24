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

import seedu.address.logic.parser.CombinedSearchPredicate;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.club.Club;
import seedu.address.model.club.ClubContainsKeywordsPredicate;
import seedu.address.model.club.ClubContainsTagsPredicate;

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

        firstPredicate.add(new ClubContainsKeywordsPredicate(Collections.singletonList("first")));
        secondPredicate.add(new ClubContainsKeywordsPredicate(Collections.singletonList("second")));

        FindClubCommand findFirstCommand = new FindClubCommand(firstPredicate);
        FindClubCommand findSecondCommand = new FindClubCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same value -> returns true
        FindClubCommand findFirstCommandCopy = new FindClubCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

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
        predicate.add(prepareClubPredicate(" "));
        FindClubCommand command = new FindClubCommand(predicate);
        expectedModel.updateFilteredClubList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredClubList());
    }

    @Test
    public void execute_multipleKeywords_multipleClubsFound() {
        String expectedMessage = String.format(MESSAGE_CLUBS_LISTED_OVERVIEW, 3);
        CombinedSearchPredicate<Club> predicate = new CombinedSearchPredicate<>();
        predicate.add(prepareClubPredicate("Archery Balls Chess"));
        FindClubCommand command = new FindClubCommand(predicate);
        expectedModel.updateFilteredClubList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ARCHERY, BALL, CHESS), model.getFilteredClubList());
    }

    @Test
    public void execute_multipleSearchParameters_multipleClubsFound() {
        String expectedMessage = String.format(MESSAGE_CLUBS_LISTED_OVERVIEW, 2);
        CombinedSearchPredicate<Club> predicate = new CombinedSearchPredicate<>();
        predicate.add(prepareClubPredicate("Archery Balls Chess"));
        predicate.add(prepareTagPredicate("sports"));
        FindClubCommand command = new FindClubCommand(predicate);
        expectedModel.updateFilteredClubList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ARCHERY, BALL), model.getFilteredClubList());
    }

    @Test
    public void toStringMethod() {
        CombinedSearchPredicate<Club> predicate = new CombinedSearchPredicate<>();
        predicate.add(new ClubContainsKeywordsPredicate(Arrays.asList("keyword")));
        FindClubCommand findCommand = new FindClubCommand(predicate);
        String expected = FindClubCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, findCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code ClubContainsKeywordsPredicate}.
     */
    private ClubContainsKeywordsPredicate prepareClubPredicate(String userInput) {
        return new ClubContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }

    /**
     * Parses {@code userInput} into a {@code TagContainsKeywordsPredicate}.
     */
    private ClubContainsTagsPredicate prepareTagPredicate(String userInput) {
        return new ClubContainsTagsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
