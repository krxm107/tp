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

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.club.Club;
import seedu.address.model.membership.Membership;
import seedu.address.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) and unit tests for RenewMembershipCommand.
 */
public class RenewMembershipCommandTest {

    private static final int VALID_DURATION = 12;
    private static final int ANOTHER_VALID_DURATION = 6;

    private Model model;

    @BeforeEach
    public void setUp() {
        // This address book has ALICE as a member of ARCHERY club
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_validRenewal_success() {
        Person personToRenew = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Club clubToRenewIn = model.getFilteredClubList().get(INDEX_FIRST_CLUB.getZeroBased());

        RenewMembershipCommand renewMembershipCommand =
                new RenewMembershipCommand(INDEX_FIRST_PERSON, INDEX_FIRST_CLUB, VALID_DURATION);

        String expectedMessage = String.format(
                RenewMembershipCommand.MESSAGE_RENEWED_MEMBERSHIP,
                personToRenew.getName(),
                clubToRenewIn.getName(),
                VALID_DURATION);

        // Create a fresh model for the expected state to ensure no object references are shared.
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Person expectedPersonToRenew = expectedModel.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Club expectedClubToRenewIn = expectedModel.getFilteredClubList().get(INDEX_FIRST_CLUB.getZeroBased());
        // Manually renew the membership in the expected model to compare states
        expectedModel.renewMembership(expectedPersonToRenew, expectedClubToRenewIn, VALID_DURATION);

        // Capture the original expiry date BEFORE the command executes on the actual model
        Membership originalMembership = model.getFilteredMembershipList().stream()
                .filter(m -> m.getPerson().isSamePerson(personToRenew) && m.getClub().isSameClub(clubToRenewIn))
                .findFirst().get();
        LocalDate expectedExpiryDate = originalMembership.getExpiryDate().plusMonths(VALID_DURATION);

        assertCommandSuccess(renewMembershipCommand, model, expectedMessage, expectedModel);

        // Verify the expiry date has been extended
        Membership renewedMembership = expectedModel.getFilteredMembershipList().stream()
                .filter(m -> m.getPerson().isSamePerson(personToRenew) && m.getClub().isSameClub(clubToRenewIn))
                .findFirst().get();

        assertTrue(renewedMembership.getExpiryDate().isEqual(expectedExpiryDate));
    }

    @Test
    public void execute_renewPendingCancellation_throwsCommandException() {
        // First, cancel the membership to set it to PENDING_CANCELLATION
        Person person = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Club club = model.getFilteredClubList().get(INDEX_FIRST_CLUB.getZeroBased());
        model.cancelMembership(person, club);

        RenewMembershipCommand renewMembershipCommand =
                new RenewMembershipCommand(INDEX_FIRST_PERSON, INDEX_FIRST_CLUB, VALID_DURATION);

        assertCommandFailure(renewMembershipCommand, model,
                "Membership is pending cancellation. Please reactivate instead.");
    }

    @Test
    public void execute_nonExistentMembership_throwsCommandException() {
        // BENSON is not in any club initially
        RenewMembershipCommand renewMembershipCommand =
                new RenewMembershipCommand(INDEX_SECOND_PERSON, INDEX_FIRST_CLUB, VALID_DURATION);

        assertCommandFailure(renewMembershipCommand, model, "Membership does not exist.");
    }

    @Test
    public void execute_invalidPersonIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        RenewMembershipCommand renewMembershipCommand =
                new RenewMembershipCommand(outOfBoundIndex, INDEX_FIRST_CLUB, VALID_DURATION);

        assertCommandFailure(renewMembershipCommand, model, String.format(
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX_DETAILED, outOfBoundIndex.getOneBased()));
    }

    @Test
    public void execute_invalidClubIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredClubList().size() + 1);
        RenewMembershipCommand renewMembershipCommand =
                new RenewMembershipCommand(INDEX_FIRST_PERSON, outOfBoundIndex, VALID_DURATION);

        assertCommandFailure(renewMembershipCommand, model, String.format(
                Messages.MESSAGE_INVALID_CLUB_DISPLAYED_INDEX_DETAILED, outOfBoundIndex.getOneBased()));
    }

    @Test
    public void execute_invalidDuration_throwsCommandException() {
        // Duration is 0, which is less than the minimum of 1
        int invalidDuration = 0;
        RenewMembershipCommand renewMembershipCommand =
                new RenewMembershipCommand(INDEX_FIRST_PERSON, INDEX_FIRST_CLUB, invalidDuration);

        String expectedErrorMessage = "Renewal duration must be between "
                + Membership.MINIMUM_RENEWAL_DURATION_IN_MONTHS + " and "
                + Membership.MAXIMUM_RENEWAL_DURATION_IN_MONTHS + " months.";

        assertCommandFailure(renewMembershipCommand, model, expectedErrorMessage);
    }

    @Test
    public void equals() {
        RenewMembershipCommand renewFirstCommand =
                new RenewMembershipCommand(INDEX_FIRST_PERSON, INDEX_FIRST_CLUB, VALID_DURATION);
        RenewMembershipCommand renewSecondCommand =
                new RenewMembershipCommand(INDEX_SECOND_PERSON, INDEX_SECOND_CLUB, ANOTHER_VALID_DURATION);

        // same object -> returns true
        assertTrue(renewFirstCommand.equals(renewFirstCommand));

        // same values -> returns true
        RenewMembershipCommand renewFirstCommandCopy =
                new RenewMembershipCommand(INDEX_FIRST_PERSON, INDEX_FIRST_CLUB, VALID_DURATION);
        assertTrue(renewFirstCommand.equals(renewFirstCommandCopy));

        // different types -> returns false
        assertFalse(renewFirstCommand.equals(1));

        // null -> returns false
        assertFalse(renewFirstCommand.equals(null));

        // different command -> returns false
        assertFalse(renewFirstCommand.equals(renewSecondCommand));

        // different person index -> returns false
        assertFalse(renewFirstCommand.equals(
                new RenewMembershipCommand(INDEX_SECOND_PERSON, INDEX_FIRST_CLUB, VALID_DURATION)));
    }
}
