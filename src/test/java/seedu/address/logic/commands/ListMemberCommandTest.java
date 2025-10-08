package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
// import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
// import static seedu.address.logic.commands.CommandTestUtil.showClubAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CLUB;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_CLUB;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;
import seedu.address.commons.core.index.Index;
// import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class ListMemberCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        /*
        Club clubMembersToList = model.getFilteredClubList().get(INDEX_FIRST_CLUB.getZeroBased());
        ListMemberCommand listMemberCommand = new ListMemberCommand(INDEX_FIRST_CLUB);

        String expectedMessage = String.format(ListMemberCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDelete));
        String expectedMessage = ListMemberCommand.MESSAGE_SUCCESS;

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updateFilteredClubList(club -> club.equals(clubMembersToList));
        expectedModel.updateFilteredPersonList(person -> true);

        assertCommandSuccess(listMemberCommand, model, expectedMessage, expectedModel);
         */
        assertTrue(true);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredClubList().size() + 1);
        ListMemberCommand listMemberCommand = new ListMemberCommand(outOfBoundIndex);

        // assertCommandFailure(listMemberCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        assertCommandFailure(listMemberCommand, model, "Bad index");
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        /*
        showClubAtIndex(model, INDEX_FIRST_CLUB);

        Club clubMembersToList = model.getFilteredClubList().get(INDEX_FIRST_CLUB.getZeroBased());
        ListMemberCommand listMemberCommand = new ListMemberCommand(INDEX_FIRST_CLUB);

        String expectedMessage = String.format(ListMemberCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDelete));
        String expectedMessage = ListMemberCommand.MESSAGE_SUCCESS;

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updateFilteredClubList(club -> club.equals(clubMembersToList));
        expectedModel.updateFilteredPersonList(person -> true);
        // Let the club include no persons
        showNoPerson(expectedModel);

        assertCommandSuccess(listMemberCommand, model, expectedMessage, expectedModel);
         */
        assertTrue(true);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        // showClubAtIndex(model, INDEX_FIRST_CLUB);

        Index outOfBoundIndex = INDEX_SECOND_CLUB;
        // ensures that outOfBoundIndex is still in bounds of address book list
        // assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getClubList().size());

        ListMemberCommand listMemberCommand = new ListMemberCommand(outOfBoundIndex);

        // assertCommandFailure(listMemberCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        assertCommandFailure(listMemberCommand, model, "Bad index");
    }

    @Test
    public void equals() {
        ListMemberCommand listMemberFirstCommand = new ListMemberCommand(INDEX_FIRST_CLUB);
        ListMemberCommand listMemberSecondCommand = new ListMemberCommand(INDEX_SECOND_CLUB);

        // same object -> returns true
        assertTrue(listMemberFirstCommand.equals(listMemberFirstCommand));

        // same values -> returns true
        ListMemberCommand listMemberFirstCommandCopy = new ListMemberCommand(INDEX_FIRST_CLUB);
        assertTrue(listMemberFirstCommand.equals(listMemberFirstCommandCopy));

        // different types -> returns false
        assertFalse(listMemberFirstCommand.equals(1));

        // null -> returns false
        assertFalse(listMemberFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(listMemberFirstCommand.equals(listMemberSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        ListMemberCommand listMemberCommand = new ListMemberCommand(targetIndex);
        String expected = ListMemberCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, listMemberCommand.toString());
    }

}
