package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_ART;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BALL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BALL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BALL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showClubAtIndex;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalClubs.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CLUB;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_CLUB;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditClubCommand.EditClubDescriptor;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.club.Club;
import seedu.address.testutil.ClubBuilder;
import seedu.address.testutil.EditClubDescriptorBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditClubCommand.
 */
public class EditClubCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Club editedClub = new ClubBuilder().build();
        EditClubDescriptor descriptor = new EditClubDescriptorBuilder(editedClub).build();
        EditClubCommand editClubCommand = new EditClubCommand(INDEX_FIRST_CLUB, descriptor);

        String expectedMessage =
                String.format(EditClubCommand.MESSAGE_EDIT_CLUB_SUCCESS,
                        Messages.format(editedClub));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setClub(model.getFilteredClubList().get(0), editedClub);

        assertCommandSuccess(editClubCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastClub = Index.fromOneBased(model.getFilteredClubList().size());
        Club lastClub = model.getFilteredClubList().get(indexLastClub.getZeroBased());

        ClubBuilder clubInList = new ClubBuilder(lastClub);
        Club editedClub = clubInList.withName(VALID_NAME_BALL).withPhone(VALID_PHONE_BALL)
                .withTags(VALID_TAG_HUSBAND).build();

        EditClubDescriptor descriptor = new EditClubDescriptorBuilder().withName(VALID_NAME_BALL)
                .withPhone(VALID_PHONE_BALL).withTags(VALID_TAG_HUSBAND).build();
        EditClubCommand editClubCommand = new EditClubCommand(indexLastClub, descriptor);

        String expectedMessage =
                String.format(EditClubCommand.MESSAGE_EDIT_CLUB_SUCCESS,
                        Messages.format(editedClub));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setClub(lastClub, editedClub);

        assertCommandSuccess(editClubCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditClubCommand editClubCommand = new EditClubCommand(INDEX_FIRST_CLUB, new EditClubDescriptor());
        Club editedClub = model.getFilteredClubList().get(INDEX_FIRST_CLUB.getZeroBased());

        String expectedMessage =
                String.format(EditClubCommand.MESSAGE_EDIT_CLUB_SUCCESS,
                        Messages.format(editedClub));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(editClubCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showClubAtIndex(model, INDEX_FIRST_CLUB);

        Club clubInFilteredList = model.getFilteredClubList().get(INDEX_FIRST_CLUB.getZeroBased());
        Club editedClub = new ClubBuilder(clubInFilteredList).withName(VALID_NAME_BALL).build();
        EditClubCommand editClubCommand = new EditClubCommand(INDEX_FIRST_CLUB,
                new EditClubDescriptorBuilder().withName(VALID_NAME_BALL).build());

        String expectedMessage =
                String.format(EditClubCommand.MESSAGE_EDIT_CLUB_SUCCESS,
                        Messages.format(editedClub));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setClub(model.getFilteredClubList().get(0), editedClub);

        assertCommandSuccess(editClubCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateClubUnfilteredList_failure() {
        Club firstClub = model.getFilteredClubList().get(INDEX_FIRST_CLUB.getZeroBased());
        EditClubDescriptor descriptor = new EditClubDescriptorBuilder(firstClub).build();
        EditClubCommand editClubCommand = new EditClubCommand(INDEX_SECOND_CLUB, descriptor);

        assertCommandFailure(editClubCommand, model, EditClubCommand.MESSAGE_DUPLICATE_CLUB);
    }

    @Test
    public void execute_duplicateClubFilteredList_failure() {
        showClubAtIndex(model, INDEX_FIRST_CLUB);

        // edit club in filtered list into a duplicate in address book
        Club clubInList = model.getAddressBook().getClubList().get(INDEX_SECOND_CLUB.getZeroBased());
        EditClubCommand editClubCommand = new EditClubCommand(INDEX_FIRST_CLUB,
                new EditClubDescriptorBuilder(clubInList).build());

        assertCommandFailure(editClubCommand, model, EditClubCommand.MESSAGE_DUPLICATE_CLUB);
    }

    @Test
    public void execute_invalidClubIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredClubList().size() + 1);
        EditClubDescriptor descriptor = new EditClubDescriptorBuilder().withName(VALID_NAME_BALL).build();
        EditClubCommand editClubCommand = new EditClubCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editClubCommand, model, Messages.MESSAGE_INVALID_CLUB_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidClubIndexFilteredList_failure() {
        showClubAtIndex(model, INDEX_FIRST_CLUB);
        Index outOfBoundIndex = INDEX_SECOND_CLUB;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getClubList().size());

        EditClubCommand editClubCommand = new EditClubCommand(outOfBoundIndex,
                new EditClubDescriptorBuilder().withName(VALID_NAME_BALL).build());

        assertCommandFailure(editClubCommand, model, Messages.MESSAGE_INVALID_CLUB_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditClubCommand standardCommand = new EditClubCommand(INDEX_FIRST_CLUB, DESC_ART);

        // same values -> returns true
        EditClubDescriptor copyDescriptor = new EditClubDescriptor(DESC_ART);
        EditClubCommand commandWithSameValues = new EditClubCommand(INDEX_FIRST_CLUB, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditClubCommand(INDEX_SECOND_CLUB, DESC_ART)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditClubCommand(INDEX_FIRST_CLUB, DESC_BALL)));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        EditClubDescriptor editClubDescriptor = new EditClubDescriptor();
        EditClubCommand editClubCommand = new EditClubCommand(index, editClubDescriptor);
        String expected = EditClubCommand.class.getCanonicalName() + "{index=" + index + ", editClubDescriptor="
                + editClubDescriptor + "}";
        assertEquals(expected, editClubCommand.toString());
    }

    @Test
    public void execute_editToDuplicateName_failure() {
        assertThrows(CommandException.class, () -> {
            Model model = new ModelManager();
            Club archery = new ClubBuilder().withName("Archery").withEmail("archery@example.com").build();
            Club ball = new ClubBuilder().withName("Ball").withEmail("ball@example.com").build();
            model.addClub(archery);
            model.addClub(ball);

            // Change Ball's name to "Archery" but keep unique email -> allowed
            EditClubDescriptor descriptor = new EditClubDescriptorBuilder().withName("Archery").build();
            EditClubCommand editBall = new EditClubCommand(Index.fromOneBased(2), descriptor);

            editBall.execute(model); // success if no exception
        });
    }
}
