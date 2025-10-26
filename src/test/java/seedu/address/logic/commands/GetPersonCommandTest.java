package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersonsAndClubs.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.parser.GetPersonMessageParser;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) for {@code GetPersonCommand}.
 */
public class GetPersonCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person personToGet = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        GetPersonCommand getPersonCommand = new GetPersonCommand(INDEX_FIRST_PERSON, "");

        String copiedText = new GetPersonMessageParser().parse(personToGet, "");
        String expectedMessage = String.format(GetPersonCommand.MESSAGE_GET_PERSON_SUCCESS, copiedText);

        assertCommandSuccess(getPersonCommand, model, expectedMessage, model);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        GetPersonCommand getPersonCommand = new GetPersonCommand(outOfBoundIndex, "");

        assertCommandFailure(getPersonCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personToGet = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        GetPersonCommand getPersonCommand = new GetPersonCommand(INDEX_FIRST_PERSON, "");

        String copiedText = new GetPersonMessageParser().parse(personToGet, "");
        String expectedMessage = String.format(GetPersonCommand.MESSAGE_GET_PERSON_SUCCESS, copiedText);

        assertCommandSuccess(getPersonCommand, model, expectedMessage, model);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        GetPersonCommand getPersonCommand = new GetPersonCommand(outOfBoundIndex, "");

        assertCommandFailure(getPersonCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_getSpecifiedFields_success() {
        Person personToGet = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        GetPersonCommand getPersonCommand = new GetPersonCommand(INDEX_FIRST_PERSON, "/aenpt");

        String copiedText = new GetPersonMessageParser().parse(personToGet, "/aenpt");
        String expectedMessage = String.format(GetPersonCommand.MESSAGE_GET_PERSON_SUCCESS, copiedText);

        assertCommandSuccess(getPersonCommand, model, expectedMessage, model);
    }

    @Test
    public void equals() {
        GetPersonCommand getPersonFirstCommand = new GetPersonCommand(INDEX_FIRST_PERSON, "");
        GetPersonCommand getPersonSecondCommand = new GetPersonCommand(INDEX_SECOND_PERSON, "");

        // same object -> returns true
        assertTrue(getPersonFirstCommand.equals(getPersonFirstCommand));

        // same values -> returns true
        GetPersonCommand getPersonFirstCommandCopy = new GetPersonCommand(INDEX_FIRST_PERSON, "");
        assertTrue(getPersonFirstCommand.equals(getPersonFirstCommandCopy));

        // different types -> returns false
        assertFalse(getPersonFirstCommand.equals(1));

        // null -> returns false
        assertFalse(getPersonFirstCommand.equals(null));

        // different club -> returns false
        assertFalse(getPersonFirstCommand.equals(getPersonSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        GetPersonCommand getPersonCommand = new GetPersonCommand(targetIndex, "");
        String expected = GetPersonCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, getPersonCommand.toString());
    }

}
