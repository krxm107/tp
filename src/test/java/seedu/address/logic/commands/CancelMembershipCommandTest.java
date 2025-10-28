package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CLUB;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_CLUB;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersonsAndClubs.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.club.Club;
import seedu.address.model.membership.Membership;
import seedu.address.model.membership.MembershipStatus;
import seedu.address.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) and unit tests for CancelMembershipCommand.
 */
public class CancelMembershipCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        // This address book has ALICE as an active member of ARCHERY club
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_cancelActiveMembership_success() {
        Person personToCancel = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Club clubToCancelIn = model.getFilteredClubList().get(INDEX_FIRST_CLUB.getZeroBased());

        CancelMembershipCommand command = new CancelMembershipCommand(INDEX_FIRST_PERSON, INDEX_FIRST_CLUB);

        String expectedMessage = String.format(
                CancelMembershipCommand.MESSAGE_CANCELLED_MEMBERSHIP,
                personToCancel.getName(),
                clubToCancelIn.getName());

        // Create a fresh model for the expected state
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Person expectedPerson = expectedModel.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Club expectedClub = expectedModel.getFilteredClubList().get(INDEX_FIRST_CLUB.getZeroBased());
        expectedModel.cancelMembership(expectedPerson, expectedClub);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);

        // Verify the status has changed
        Membership cancelledMembership = expectedModel.getFilteredMembershipList().stream()
                .filter(m -> m.getPerson().isSamePerson(expectedPerson) && m.getClub().isSameClub(expectedClub))
                .findFirst().get();
        assertTrue(cancelledMembership.getStatus() == MembershipStatus.PENDING_CANCELLATION
                || cancelledMembership.getStatus() == MembershipStatus.CANCELLED);
    }

    @Test
    public void execute_cancelAlreadyCancelledMembership_throwsCommandException() {
        // First, cancel the membership
        Person person = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Club club = model.getFilteredClubList().get(INDEX_FIRST_CLUB.getZeroBased());
        model.cancelMembership(person, club);
        // Manually set it to CANCELLED for this test case
        model.getFilteredMembershipList().stream()
                .filter(m -> m.getPerson().isSamePerson(person) && m.getClub().isSameClub(club))
                .findFirst().get().updateStatus(); // This will move it to CANCELLED if expired, or keep as PENDING
        // To be certain, we can manually set it
        model.getFilteredMembershipList().stream()
                .filter(m -> m.getPerson().isSamePerson(person) && m.getClub().isSameClub(club))
                .findFirst().get().statusProperty().set(MembershipStatus.CANCELLED);

        // Then, try to cancel it again
        CancelMembershipCommand command = new CancelMembershipCommand(INDEX_FIRST_PERSON, INDEX_FIRST_CLUB);

        assertCommandFailure(command, model, Membership.MESSAGE_ALREADY_CANCELLED);
    }

    @Test
    public void execute_cancelPendingCancellationMembership_throwsCommandException() {
        // First, cancel the membership, which will set it to PENDING_CANCELLATION
        Person person = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Club club = model.getFilteredClubList().get(INDEX_FIRST_CLUB.getZeroBased());
        model.cancelMembership(person, club);

        // Then, try to cancel it again
        CancelMembershipCommand command = new CancelMembershipCommand(INDEX_FIRST_PERSON, INDEX_FIRST_CLUB);

        assertCommandFailure(command, model, Membership.MESSAGE_IS_PENDING_CANCELLATION);
    }

    @Test
    public void execute_nonExistentMembership_throwsCommandException() {
        // BENSON is not in any club initially
        CancelMembershipCommand command = new CancelMembershipCommand(INDEX_SECOND_PERSON, INDEX_FIRST_CLUB);

        assertCommandFailure(command, model, "Membership does not exist.");
    }

    @Test
    public void execute_invalidPersonIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        CancelMembershipCommand command = new CancelMembershipCommand(outOfBoundIndex, INDEX_FIRST_CLUB);

        assertCommandFailure(command, model, String.format(
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX_DETAILED, outOfBoundIndex.getOneBased()));
    }

    @Test
    public void execute_invalidClubIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredClubList().size() + 1);
        CancelMembershipCommand command = new CancelMembershipCommand(INDEX_FIRST_PERSON, outOfBoundIndex);

        assertCommandFailure(command, model, String.format(
                Messages.MESSAGE_INVALID_CLUB_DISPLAYED_INDEX_DETAILED, outOfBoundIndex.getOneBased()));
    }

    @Test
    public void equals() {
        CancelMembershipCommand cancelFirstCommand =
                new CancelMembershipCommand(INDEX_FIRST_PERSON, INDEX_FIRST_CLUB);
        CancelMembershipCommand cancelSecondCommand =
                new CancelMembershipCommand(INDEX_SECOND_PERSON, INDEX_SECOND_CLUB);

        // same object -> returns true
        assertTrue(cancelFirstCommand.equals(cancelFirstCommand));

        // same values -> returns true
        CancelMembershipCommand cancelFirstCommandCopy =
                new CancelMembershipCommand(INDEX_FIRST_PERSON, INDEX_FIRST_CLUB);
        assertTrue(cancelFirstCommand.equals(cancelFirstCommandCopy));

        // different types -> returns false
        assertFalse(cancelFirstCommand.equals(1));

        // null -> returns false
        assertFalse(cancelFirstCommand.equals(null));

        // different command -> returns false
        assertFalse(cancelFirstCommand.equals(cancelSecondCommand));
    }
}
