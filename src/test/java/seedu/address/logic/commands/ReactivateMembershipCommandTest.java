package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CLUB;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_CLUB;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.club.Club;
import seedu.address.model.membership.Membership;
import seedu.address.model.membership.MembershipStatus;
import seedu.address.model.person.Person;
import seedu.address.testutil.ClubBuilder;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ReactivateMembershipCommand.
 */
public class ReactivateMembershipCommandTest {

    private static final int VALID_DURATION = 12;
    private static final int ANOTHER_VALID_DURATION = 6;

    private Model model = new ModelManager(new AddressBook(), new UserPrefs());

    @Test
    public void execute_reactivateExpiredMembership_success() {
        // Setup model with an expired membership
        Person person = new PersonBuilder().build();
        Club club = new ClubBuilder().build();
        LocalDate pastDate = LocalDate.now().minusYears(1);
        Membership expiredMembership = new Membership(person, club, pastDate, pastDate,
                new ArrayList<>(), MembershipStatus.EXPIRED);
        // Manually link the membership to the person and club for a consistent state
        person.addMembership(expiredMembership);
        club.addMembership(expiredMembership);
        model.addPerson(person);
        model.addClub(club);
        model.addMembership(expiredMembership);

        ReactivateMembershipCommand command =
                new ReactivateMembershipCommand(INDEX_FIRST_PERSON, INDEX_FIRST_CLUB, VALID_DURATION);

        String expectedMessage = String.format(
                ReactivateMembershipCommand.MESSAGE_REACTIVATED_MEMBERSHIP,
                person.getName(), club.getName(), VALID_DURATION);

        // Setup expected model
        Model expectedModel = new ModelManager(new AddressBook(), new UserPrefs());
        Person expectedPersonToAdd = new PersonBuilder(person).build();
        Club expectedClubToAdd = new ClubBuilder(club).build();
        Membership expectedExpiredMembership =
                new Membership(expectedPersonToAdd, expectedClubToAdd, pastDate, pastDate,
                new ArrayList<>(), MembershipStatus.EXPIRED);
        expectedModel.addPerson(expectedPersonToAdd);
        expectedModel.addClub(expectedClubToAdd);
        expectedModel.addMembership(expectedExpiredMembership);
        Person expectedPerson = expectedModel.getFilteredPersonList().get(0);
        Club expectedClub = expectedModel.getFilteredClubList().get(0);
        expectedModel.reactivateMembership(expectedPerson, expectedClub, VALID_DURATION);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_reactivateCancelledMembership_success() {
        // Setup model with a cancelled membership
        Person person = new PersonBuilder().build();
        Club club = new ClubBuilder().build();
        LocalDate pastDate = LocalDate.now().minusYears(1);
        Membership cancelledMembership = new Membership(person, club, pastDate, pastDate,
                new ArrayList<>(), MembershipStatus.CANCELLED);
        // Manually link the membership to the person and club for a consistent state
        person.addMembership(cancelledMembership);
        club.addMembership(cancelledMembership);
        model.addPerson(person);
        model.addClub(club);
        model.addMembership(cancelledMembership);

        ReactivateMembershipCommand command =
                new ReactivateMembershipCommand(INDEX_FIRST_PERSON, INDEX_FIRST_CLUB, VALID_DURATION);

        String expectedMessage = String.format(
                ReactivateMembershipCommand.MESSAGE_REACTIVATED_MEMBERSHIP,
                person.getName(), club.getName(), VALID_DURATION);

        // Setup expected model
        Model expectedModel = new ModelManager(new AddressBook(), new UserPrefs());
        Person expectedPersonToAdd = new PersonBuilder(person).build();
        Club expectedClubToAdd = new ClubBuilder(club).build();
        Membership expectedCancelledMembership =
                new Membership(expectedPersonToAdd, expectedClubToAdd, pastDate, pastDate,
                new ArrayList<>(), MembershipStatus.CANCELLED);
        expectedModel.addPerson(expectedPersonToAdd);
        expectedModel.addClub(expectedClubToAdd);
        expectedModel.addMembership(expectedCancelledMembership);
        Person expectedPerson = expectedModel.getFilteredPersonList().get(0);
        Club expectedClub = expectedModel.getFilteredClubList().get(0);
        expectedModel.reactivateMembership(expectedPerson, expectedClub, VALID_DURATION);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_reactivateActiveMembership_throwsCommandException() {
        // Setup model with an active membership
        Person person = new PersonBuilder().build();
        Club club = new ClubBuilder().build();
        Membership activeMembership = new Membership(person, club); // Defaults to active
        // Manually link the membership to the person and club
        person.addMembership(activeMembership);
        club.addMembership(activeMembership);
        model.addPerson(person);
        model.addClub(club);
        model.addMembership(activeMembership);

        ReactivateMembershipCommand command =
                new ReactivateMembershipCommand(INDEX_FIRST_PERSON, INDEX_FIRST_CLUB, VALID_DURATION);

        assertCommandFailure(command, model, "Only expired or cancelled memberships can be reactivated.");
    }

    @Test
    public void execute_nonExistentMembership_throwsCommandException() {
        Person person = new PersonBuilder().build();
        Club club = new ClubBuilder().build();
        model.addPerson(person);
        model.addClub(club); // No membership added

        ReactivateMembershipCommand command =
                new ReactivateMembershipCommand(INDEX_FIRST_PERSON, INDEX_FIRST_CLUB, VALID_DURATION);

        assertCommandFailure(command, model, "Membership does not exist.");
    }

    @Test
    public void execute_invalidPersonIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        ReactivateMembershipCommand command =
                new ReactivateMembershipCommand(outOfBoundIndex, INDEX_FIRST_CLUB, VALID_DURATION);

        assertCommandFailure(command, model, String.format(
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX_DETAILED, outOfBoundIndex.getOneBased()));
    }

    @Test
    public void execute_invalidClubIndex_throwsCommandException() {
        Person person = new PersonBuilder().build();
        model.addPerson(person);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredClubList().size() + 1);
        ReactivateMembershipCommand command =
                new ReactivateMembershipCommand(INDEX_FIRST_PERSON, outOfBoundIndex, VALID_DURATION);

        assertCommandFailure(command, model, String.format(
                Messages.MESSAGE_INVALID_CLUB_DISPLAYED_INDEX_DETAILED, outOfBoundIndex.getOneBased()));
    }

    @Test
    public void equals() {
        ReactivateMembershipCommand reactivateFirstCommand =
                new ReactivateMembershipCommand(INDEX_FIRST_PERSON, INDEX_FIRST_CLUB, VALID_DURATION);
        ReactivateMembershipCommand reactivateSecondCommand =
                new ReactivateMembershipCommand(INDEX_SECOND_PERSON, INDEX_SECOND_CLUB, ANOTHER_VALID_DURATION);

        // same object -> returns true
        assertTrue(reactivateFirstCommand.equals(reactivateFirstCommand));

        // same values -> returns true
        ReactivateMembershipCommand reactivateFirstCommandCopy =
                new ReactivateMembershipCommand(INDEX_FIRST_PERSON, INDEX_FIRST_CLUB, VALID_DURATION);
        assertTrue(reactivateFirstCommand.equals(reactivateFirstCommandCopy));

        // different types -> returns false
        assertFalse(reactivateFirstCommand.equals(1));

        // null -> returns false
        assertFalse(reactivateFirstCommand.equals(null));

        // different command -> returns false
        assertFalse(reactivateFirstCommand.equals(reactivateSecondCommand));

        // different duration -> returns false
        assertFalse(reactivateFirstCommand.equals(
                new ReactivateMembershipCommand(INDEX_FIRST_PERSON, INDEX_FIRST_CLUB, ANOTHER_VALID_DURATION)));
    }
}
