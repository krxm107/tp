package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showClubAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalClubs.getTypicalAddressBook;

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
import seedu.address.testutil.EditClubDescriptorBuilder;
import seedu.address.testutil.ClubBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditClubCommand.
 */
public class EditClubCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Club editedClub = new ClubBuilder().build();
        EditClubDescriptor descriptor = new EditClubDescriptorBuilder(editedClub).build();
        EditClubCommand editClubCommand = new EditClubCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage =
                String.format(EditClubCommand.MESSAGE_EDIT_PERSON_SUCCESS,
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
        Club editedClub = clubInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withTags(VALID_TAG_HUSBAND).build();

        EditClubDescriptor descriptor = new EditClubDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_HUSBAND).build();
        EditClubCommand editClubCommand = new EditClubCommand(indexLastClub, descriptor);

        String expectedMessage =
                String.format(EditClubCommand.MESSAGE_EDIT_PERSON_SUCCESS,
                        Messages.format(editedClub));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setClub(lastClub, editedClub);

        assertCommandSuccess(editClubCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditClubCommand editClubCommand = new EditClubCommand(INDEX_FIRST_PERSON, new EditClubDescriptor());
        Club editedClub = model.getFilteredClubList().get(INDEX_FIRST_PERSON.getZeroBased());

        String expectedMessage =
                String.format(EditClubCommand.MESSAGE_EDIT_PERSON_SUCCESS,
                        Messages.format(editedClub));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(editClubCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showClubAtIndex(model, INDEX_FIRST_PERSON);

        Club clubInFilteredList = model.getFilteredClubList().get(INDEX_FIRST_PERSON.getZeroBased());
        Club editedClub = new ClubBuilder(clubInFilteredList).withName(VALID_NAME_BOB).build();
        EditClubCommand editClubCommand = new EditClubCommand(INDEX_FIRST_PERSON,
                new EditClubDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage =
                String.format(EditClubCommand.MESSAGE_EDIT_PERSON_SUCCESS,
                        Messages.format(editedClub));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setClub(model.getFilteredClubList().get(0), editedClub);

        assertCommandSuccess(editClubCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateClubUnfilteredList_failure() {
        Club firstClub = model.getFilteredClubList().get(INDEX_FIRST_PERSON.getZeroBased());
        EditClubDescriptor descriptor = new EditClubDescriptorBuilder(firstClub).build();
        EditClubCommand editClubCommand = new EditClubCommand(INDEX_SECOND_PERSON, descriptor);

        assertCommandFailure(editClubCommand, model, EditClubCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_duplicateClubFilteredList_failure() {
        showClubAtIndex(model, INDEX_FIRST_PERSON);

        // edit club in filtered list into a duplicate in address book
        Club clubInList = model.getAddressBook().getClubList().get(INDEX_SECOND_PERSON.getZeroBased());
        EditClubCommand editClubCommand = new EditClubCommand(INDEX_FIRST_PERSON,
                new EditClubDescriptorBuilder(clubInList).build());

        assertCommandFailure(editClubCommand, model, EditClubCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_invalidClubIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredClubList().size() + 1);
        EditClubDescriptor descriptor = new EditClubDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditClubCommand editClubCommand = new EditClubCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editClubCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidClubIndexFilteredList_failure() {
        showClubAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getClubList().size());

        EditClubCommand editClubCommand = new EditClubCommand(outOfBoundIndex,
                new EditClubDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editClubCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditClubCommand standardCommand = new EditClubCommand(INDEX_FIRST_PERSON, DESC_AMY);

        // same values -> returns true
        EditClubDescriptor copyDescriptor = new EditClubDescriptor(DESC_AMY);
        EditClubCommand commandWithSameValues = new EditClubCommand(INDEX_FIRST_PERSON, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditClubCommand(INDEX_SECOND_PERSON, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditClubCommand(INDEX_FIRST_PERSON, DESC_BOB)));
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
    public void execute_editToDuplicateEmail_failure() {
        Model model = new ModelManager();
        Club alice = new ClubBuilder().withName("Alice").withEmail("alice@example.com").build();
        Club bob = new ClubBuilder().withName("Bob").withEmail("bob@example.com").build();
        model.addClub(alice);
        model.addClub(bob);

        // Try to change Bob's email to Alice's -> should fail
        EditClubDescriptor descriptor = new EditClubDescriptorBuilder().withEmail("alice@example.com").build();
        EditClubCommand editBob = new EditClubCommand(Index.fromOneBased(2), descriptor);

        assertThrows(CommandException.class, () -> editBob.execute(model));
    }

    @Test
    public void execute_editToDuplicateName_success() throws Exception {
        Model model = new ModelManager();
        Club alice = new ClubBuilder().withName("Alice").withEmail("alice@example.com").build();
        Club bob = new ClubBuilder().withName("Bob").withEmail("bob@example.com").build();
        model.addClub(alice);
        model.addClub(bob);

        // Change Bob's name to "Alice" but keep unique email -> allowed
        EditClubDescriptor descriptor = new EditClubDescriptorBuilder().withName("Alice").build();
        EditClubCommand editBob = new EditClubCommand(Index.fromOneBased(2), descriptor);

        editBob.execute(model); // success if no exception
    }
}
