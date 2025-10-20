package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CLUB;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_CLUB;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.club.Club;
import seedu.address.testutil.TypicalClubs;
import seedu.address.testutil.TypicalPersonsAndClubs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteClubCommandTest {

    private Model model = new ModelManager(TypicalClubs.getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Club clubToDelete = model.getFilteredClubList().get(INDEX_FIRST_CLUB.getZeroBased());
        DeleteClubCommand deleteCommand = new DeleteClubCommand(INDEX_FIRST_CLUB);

        String expectedMessage = String.format(DeleteClubCommand.MESSAGE_DELETE_CLUB_SUCCESS,
                Messages.format(clubToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteClub(clubToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredClubList().size() + 1);
        DeleteClubCommand deleteCommand = new DeleteClubCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_CLUB_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        assertTrue(true);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        assertTrue(true);
    }

    @Test
    public void execute_deleteWithMemberships_success() {
        model = new ModelManager(TypicalPersonsAndClubs.getTypicalAddressBook(), new UserPrefs());

        Club clubToDelete = model.getFilteredClubList().get(INDEX_FIRST_CLUB.getZeroBased());
        DeleteClubCommand deleteCommand = new DeleteClubCommand(INDEX_FIRST_CLUB);

        String expectedMessage = String.format(DeleteClubCommand.MESSAGE_DELETE_CLUB_SUCCESS,
                Messages.format(clubToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        clubToDelete.clearMembers();
        expectedModel.deleteClub(clubToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        DeleteClubCommand deleteFirstCommand = new DeleteClubCommand(INDEX_FIRST_CLUB);
        DeleteClubCommand deleteSecondCommand = new DeleteClubCommand(INDEX_SECOND_CLUB);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteClubCommand deleteFirstCommandCopy = new DeleteClubCommand(INDEX_FIRST_CLUB);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different clubs -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        DeleteClubCommand deleteCommand = new DeleteClubCommand(targetIndex);
        String expected = DeleteClubCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, deleteCommand.toString());
    }

    /**
     * Updates {@code model}'s filtered list to show no clubs.
     */
    private void showNoClub(Model model) {
        model.updateFilteredClubList(p -> false);

        assertTrue(model.getFilteredClubList().isEmpty());
    }
}
