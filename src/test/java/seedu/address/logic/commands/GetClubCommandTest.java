package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showClubAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CLUB;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_CLUB;
import static seedu.address.testutil.TypicalPersonsAndClubs.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.parser.GetClubMessageParser;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.club.Club;

/**
 * Contains integration tests (interaction with the Model) for {@code GetClubCommand}.
 */
public class GetClubCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Club clubToGet = model.getFilteredClubList().get(INDEX_FIRST_CLUB.getZeroBased());
        GetClubCommand getClubCommand = new GetClubCommand(INDEX_FIRST_CLUB, "");

        String copiedText = new GetClubMessageParser().parse(clubToGet, "");
        String expectedMessage = String.format(GetClubCommand.MESSAGE_GET_CLUB_SUCCESS, copiedText);

        assertCommandSuccess(getClubCommand, model, expectedMessage, model);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredClubList().size() + 1);
        GetClubCommand getClubCommand = new GetClubCommand(outOfBoundIndex, "");

        assertCommandFailure(getClubCommand, model, Messages.MESSAGE_INVALID_CLUB_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showClubAtIndex(model, INDEX_FIRST_CLUB);

        Club clubToGet = model.getFilteredClubList().get(INDEX_FIRST_CLUB.getZeroBased());
        GetClubCommand getClubCommand = new GetClubCommand(INDEX_FIRST_CLUB, "");

        String copiedText = new GetClubMessageParser().parse(clubToGet, "");
        String expectedMessage = String.format(GetClubCommand.MESSAGE_GET_CLUB_SUCCESS, copiedText);

        assertCommandSuccess(getClubCommand, model, expectedMessage, model);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showClubAtIndex(model, INDEX_FIRST_CLUB);

        Index outOfBoundIndex = INDEX_SECOND_CLUB;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getClubList().size());

        GetClubCommand getClubCommand = new GetClubCommand(outOfBoundIndex, "");

        assertCommandFailure(getClubCommand, model, Messages.MESSAGE_INVALID_CLUB_DISPLAYED_INDEX);
    }

    @Test
    public void execute_getSpecifiedFields_success() {
        Club clubToGet = model.getFilteredClubList().get(INDEX_FIRST_CLUB.getZeroBased());
        GetClubCommand getClubCommand = new GetClubCommand(INDEX_FIRST_CLUB, "/aenpt");

        String copiedText = new GetClubMessageParser().parse(clubToGet, "/aenpt");
        String expectedMessage = String.format(GetClubCommand.MESSAGE_GET_CLUB_SUCCESS, copiedText);

        assertCommandSuccess(getClubCommand, model, expectedMessage, model);
    }

    @Test
    public void execute_getFullFields_success() {
        Club clubToGet = model.getFilteredClubList().get(INDEX_FIRST_CLUB.getZeroBased());
        GetClubCommand getClubCommand = new GetClubCommand(INDEX_FIRST_CLUB, "/*");

        String copiedText = new GetClubMessageParser().parse(clubToGet, "/*");
        String expectedMessage = String.format(GetClubCommand.MESSAGE_GET_CLUB_SUCCESS, copiedText);

        assertCommandSuccess(getClubCommand, model, expectedMessage, model);
    }

    @Test
    public void equals() {
        GetClubCommand getClubFirstCommand = new GetClubCommand(INDEX_FIRST_CLUB, "");
        GetClubCommand getClubSecondCommand = new GetClubCommand(INDEX_SECOND_CLUB, "");

        // same object -> returns true
        assertTrue(getClubFirstCommand.equals(getClubFirstCommand));

        // same values -> returns true
        GetClubCommand getClubFirstCommandCopy = new GetClubCommand(INDEX_FIRST_CLUB, "");
        assertTrue(getClubFirstCommand.equals(getClubFirstCommandCopy));

        // different types -> returns false
        assertFalse(getClubFirstCommand.equals(1));

        // null -> returns false
        assertFalse(getClubFirstCommand.equals(null));

        // different club -> returns false
        assertFalse(getClubFirstCommand.equals(getClubSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        GetClubCommand getClubCommand = new GetClubCommand(targetIndex, "");
        String expected = GetClubCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, getClubCommand.toString());
    }

}
