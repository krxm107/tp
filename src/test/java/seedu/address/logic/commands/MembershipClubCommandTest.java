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

import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.search.predicates.MembershipStatusPredicate;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.club.Club;
import seedu.address.model.membership.Membership;
import seedu.address.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code MembershipClubCommand}.
 */
public class MembershipClubCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Predicate<Membership> predicate = new MembershipStatusPredicate();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Club clubMembersToList = model.getFilteredClubList().get(INDEX_FIRST_CLUB.getZeroBased());
        MembershipClubCommand membershipClubCommand = new MembershipClubCommand(INDEX_FIRST_CLUB, predicate);

        String expectedMessage = MembershipClubCommand.MESSAGE_SUCCESS;

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updateFilteredClubList(club -> club.equals(clubMembersToList));
        Predicate<Person> isInClub = person -> person.getMemberships().stream().map(Membership::getClub)
                .anyMatch(club -> club.equals(clubMembersToList));
        expectedModel.updateFilteredPersonList(isInClub);

        assertCommandSuccess(membershipClubCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredClubList().size() + 1);
        MembershipClubCommand membershipClubCommand = new MembershipClubCommand(outOfBoundIndex, predicate);

        assertCommandFailure(membershipClubCommand, model, Messages.MESSAGE_INVALID_CLUB_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showClubAtIndex(model, INDEX_FIRST_CLUB);

        Club clubMembersToList = model.getFilteredClubList().get(INDEX_FIRST_CLUB.getZeroBased());
        MembershipClubCommand membershipClubCommand = new MembershipClubCommand(INDEX_FIRST_CLUB, predicate);

        String expectedMessage = MembershipClubCommand.MESSAGE_SUCCESS;

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updateFilteredClubList(club -> club.equals(clubMembersToList));
        Predicate<Person> isInClub = person -> person.getMemberships().stream().map(Membership::getClub)
                .anyMatch(club -> club.equals(clubMembersToList));
        expectedModel.updateFilteredPersonList(isInClub);

        assertCommandSuccess(membershipClubCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showClubAtIndex(model, INDEX_FIRST_CLUB);

        Index outOfBoundIndex = INDEX_SECOND_CLUB;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getClubList().size());

        MembershipClubCommand membershipClubCommand = new MembershipClubCommand(outOfBoundIndex, predicate);

        assertCommandFailure(membershipClubCommand, model, Messages.MESSAGE_INVALID_CLUB_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        MembershipClubCommand listMemberFirstCommand = new MembershipClubCommand(INDEX_FIRST_CLUB, predicate);
        MembershipClubCommand listMemberSecondCommand = new MembershipClubCommand(INDEX_SECOND_CLUB, predicate);

        // same object -> returns true
        assertTrue(listMemberFirstCommand.equals(listMemberFirstCommand));

        // same values -> returns true
        MembershipClubCommand listMemberFirstCommandCopy = new MembershipClubCommand(INDEX_FIRST_CLUB, predicate);
        assertTrue(listMemberFirstCommand.equals(listMemberFirstCommandCopy));

        // different types -> returns false
        assertFalse(listMemberFirstCommand.equals(1));

        // null -> returns false
        assertFalse(listMemberFirstCommand.equals(null));

        // different club -> returns false
        assertFalse(listMemberFirstCommand.equals(listMemberSecondCommand));

        // different predicate -> returns false
        MembershipStatusPredicate differentPredicate = new MembershipStatusPredicate();
        differentPredicate.addPredicate("*");
        MembershipClubCommand listMemberThirdCommand = new MembershipClubCommand(INDEX_FIRST_CLUB, differentPredicate);
        assertFalse(listMemberFirstCommand.equals(listMemberThirdCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        MembershipClubCommand membershipClubCommand = new MembershipClubCommand(targetIndex, predicate);
        String expected = MembershipClubCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + ", "
                + "predicate=" + predicate + "}";
        assertEquals(expected, membershipClubCommand.toString());
    }

}
