package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalClubs.ARCHERY;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.club.Club;
import seedu.address.model.membership.Membership;
import seedu.address.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) for {@code GetHistoryCommand}.
 */
public class GetHistoryCommandTest {

    private Model model;

    /**
     * A clone of GetHistoryCommand that does not interact with the clipboard.
     */
    private static class GetHistoryCommandClone extends GetHistoryCommand {
        public GetHistoryCommandClone(Index targetIndex) {
            super(targetIndex);
        }

        @Override
        public void copyToClipboard(String details, Person person) throws CommandException {
            // Do nothing to avoid clipboard interaction in tests
        }
    }

    @BeforeEach
    public void setUp() {
        AddressBook ab = new AddressBook();
        ab.addPerson(ALICE);
        ab.addPerson(BENSON);
        ab.addClub(ARCHERY);
        model = new ModelManager(ab, new UserPrefs());
    }

    @Test
    public void execute_validIndexWithMemberships_success() {
        // Setup: Add a membership to the first person
        Person personToGet = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Club club = model.getFilteredClubList().get(0);
        Membership membership = new Membership(personToGet, club);

        // Add the membership to the model, person, and club to simulate the full command behavior
        model.addMembership(membership);
        personToGet.addMembership(membership);
        club.addMembership(membership);

        GetHistoryCommand getHistoryCommand = new GetHistoryCommandClone(INDEX_FIRST_PERSON);

        // Manually format the expected history string
        StringBuilder expectedHistory = new StringBuilder();
        expectedHistory.append("Membership history for ").append(personToGet.getName().fullName).append(":\n");
        expectedHistory.append("\nClub: ").append(membership.getClubName()).append("\n");
        membership.getMembershipEventHistory().forEach(event ->
                expectedHistory.append("  - ").append(event.toString()).append("\n"));

        String expectedMessage = String.format(GetHistoryCommand.MESSAGE_GET_HISTORY_SUCCESS,
                personToGet.getName().fullName, expectedHistory.toString());

        assertCommandSuccess(getHistoryCommand, model, expectedMessage, model);
    }

    @Test
    public void execute_validIndexNoMemberships_success() {
        // The second person in the typical address book has no memberships by default
        Person personToGet = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        GetHistoryCommand getHistoryCommand = new GetHistoryCommandClone(INDEX_SECOND_PERSON);

        String expectedMessage = String.format(GetHistoryCommand.MESSAGE_NO_MEMBERSHIPS,
                personToGet.getName().fullName);

        assertCommandSuccess(getHistoryCommand, model, expectedMessage, model);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        GetHistoryCommand getHistoryCommand = new GetHistoryCommandClone(outOfBoundIndex);

        assertCommandFailure(getHistoryCommand, model,
                String.format(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX_DETAILED, outOfBoundIndex.getOneBased()));
    }

    @Test
    public void equals() {
        GetHistoryCommand getHistoryFirstCommand = new GetHistoryCommand(INDEX_FIRST_PERSON);
        GetHistoryCommand getHistorySecondCommand = new GetHistoryCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(getHistoryFirstCommand.equals(getHistoryFirstCommand));

        // same values -> returns true
        GetHistoryCommand getHistoryFirstCommandCopy = new GetHistoryCommand(INDEX_FIRST_PERSON);
        assertTrue(getHistoryFirstCommand.equals(getHistoryFirstCommandCopy));

        // different types -> returns false
        assertFalse(getHistoryFirstCommand.equals(1));

        // null -> returns false
        assertFalse(getHistoryFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(getHistoryFirstCommand.equals(getHistorySecondCommand));
    }
}
