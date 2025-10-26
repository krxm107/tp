package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
//import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.logic.commands.CommandTestUtil.*;
import static seedu.address.testutil.TypicalIndexes.*;
import static seedu.address.testutil.TypicalPersonsAndClubs.getTypicalAddressBook;

import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.parser.MembershipStatusPredicate;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.club.Club;
import seedu.address.model.membership.Membership;
import seedu.address.model.person.Person;
/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code ListMembershipCommand}.
 */
public class ListMembershipCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Predicate<Membership> predicate = new MembershipStatusPredicate();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person personMembershipsToList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        ListMembershipCommand listMembershipCommand = new ListMembershipCommand(INDEX_FIRST_PERSON, predicate);

        String expectedMessage = ListMembershipCommand.MESSAGE_SUCCESS;

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updateFilteredPersonList(person -> person.equals(personMembershipsToList));
        Predicate<Club> isMemberOf = club -> club.getMemberships().stream().map(Membership::getPerson)
                .anyMatch(person -> person.equals(personMembershipsToList));
        expectedModel.updateFilteredClubList(isMemberOf);

        assertCommandSuccess(listMembershipCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        ListMembershipCommand listMembershipCommand = new ListMembershipCommand(outOfBoundIndex, predicate);

        assertCommandFailure(listMembershipCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personMembershipsToList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        ListMembershipCommand listMembershipCommand = new ListMembershipCommand(INDEX_FIRST_PERSON, predicate);

        String expectedMessage = ListMembershipCommand.MESSAGE_SUCCESS;

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updateFilteredPersonList(person -> person.equals(personMembershipsToList));
        Predicate<Club> isMemberOf = club -> club.getMemberships().stream().map(Membership::getPerson)
                .anyMatch(person -> person.equals(personMembershipsToList));
        expectedModel.updateFilteredClubList(isMemberOf);

        assertCommandSuccess(listMembershipCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getClubList().size());

        ListMembershipCommand listMembershipCommand = new ListMembershipCommand(outOfBoundIndex, predicate);

        assertCommandFailure(listMembershipCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        ListMembershipCommand listMembershipFirstCommand = new ListMembershipCommand(INDEX_FIRST_PERSON, predicate);
        ListMembershipCommand listMembershipSecondCommand = new ListMembershipCommand(INDEX_SECOND_PERSON, predicate);

        // same object -> returns true
        assertTrue(listMembershipFirstCommand.equals(listMembershipFirstCommand));

        // same values -> returns true
        ListMembershipCommand listMembershipFirstCommandCopy = new ListMembershipCommand(INDEX_FIRST_PERSON, predicate);
        assertTrue(listMembershipFirstCommand.equals(listMembershipFirstCommandCopy));

        // different types -> returns false
        assertFalse(listMembershipFirstCommand.equals(1));

        // null -> returns false
        assertFalse(listMembershipFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(listMembershipFirstCommand.equals(listMembershipSecondCommand));

        // different predicate -> returns false
        MembershipStatusPredicate differentPredicate = new MembershipStatusPredicate();
        differentPredicate.addPredicate("*");
        ListMembershipCommand listMembershipThirdCommand =
                new ListMembershipCommand(INDEX_FIRST_CLUB, differentPredicate);
        assertFalse(listMembershipFirstCommand.equals(listMembershipThirdCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        ListMembershipCommand listMembershipCommand = new ListMembershipCommand(targetIndex, predicate);
        String expected = ListMembershipCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + ", "
                + "predicate=" + predicate + "}";
        assertEquals(expected, listMembershipCommand.toString());
    }

}
