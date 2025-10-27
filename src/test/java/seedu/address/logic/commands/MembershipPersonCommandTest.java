package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
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
import seedu.address.model.membership.MembershipStatus;
import seedu.address.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code MembershipPersonCommand}.
 */
public class MembershipPersonCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Predicate<Membership> predicate = new MembershipStatusPredicate(MembershipStatus.getDefaultStatuses());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person personMembershipsToList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        MembershipPersonCommand membershipPersonCommand = new MembershipPersonCommand(INDEX_FIRST_PERSON, predicate);

        String expectedMessage = MembershipPersonCommand.MESSAGE_SUCCESS;

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updateFilteredPersonList(person -> person.equals(personMembershipsToList));
        Predicate<Club> isMemberOf = club -> club.getMemberships().stream().map(Membership::getPerson)
                .anyMatch(person -> person.equals(personMembershipsToList));
        expectedModel.updateFilteredClubList(isMemberOf);

        assertCommandSuccess(membershipPersonCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        MembershipPersonCommand membershipPersonCommand = new MembershipPersonCommand(outOfBoundIndex, predicate);

        assertCommandFailure(membershipPersonCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personMembershipsToList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        MembershipPersonCommand membershipPersonCommand = new MembershipPersonCommand(INDEX_FIRST_PERSON, predicate);

        String expectedMessage = MembershipPersonCommand.MESSAGE_SUCCESS;

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updateFilteredPersonList(person -> person.equals(personMembershipsToList));
        Predicate<Club> isMemberOf = club -> club.getMemberships().stream().map(Membership::getPerson)
                .anyMatch(person -> person.equals(personMembershipsToList));
        expectedModel.updateFilteredClubList(isMemberOf);

        assertCommandSuccess(membershipPersonCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getClubList().size());

        MembershipPersonCommand membershipPersonCommand = new MembershipPersonCommand(outOfBoundIndex, predicate);

        assertCommandFailure(membershipPersonCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        MembershipPersonCommand listMembershipFirstCommand = new MembershipPersonCommand(INDEX_FIRST_PERSON, predicate);
        MembershipPersonCommand listMembershipSecondCommand =
                new MembershipPersonCommand(INDEX_SECOND_PERSON, predicate);

        // same object -> returns true
        assertTrue(listMembershipFirstCommand.equals(listMembershipFirstCommand));

        // same values -> returns true
        MembershipPersonCommand listMembershipFirstCommandCopy =
                new MembershipPersonCommand(INDEX_FIRST_PERSON, predicate);
        assertTrue(listMembershipFirstCommand.equals(listMembershipFirstCommandCopy));

        // different types -> returns false
        assertFalse(listMembershipFirstCommand.equals(1));

        // null -> returns false
        assertFalse(listMembershipFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(listMembershipFirstCommand.equals(listMembershipSecondCommand));

        // different predicate -> returns false
        MembershipStatusPredicate differentPredicate =
                new MembershipStatusPredicate(MembershipStatus.getStatuses("a"));
        MembershipPersonCommand listMembershipThirdCommand =
                new MembershipPersonCommand(INDEX_FIRST_PERSON, differentPredicate);
        assertFalse(listMembershipFirstCommand.equals(listMembershipThirdCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        MembershipPersonCommand membershipPersonCommand = new MembershipPersonCommand(targetIndex, predicate);
        String expected = MembershipPersonCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + ", "
                + "predicate=" + predicate + "}";
        assertEquals(expected, membershipPersonCommand.toString());
    }

}
