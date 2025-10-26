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
import seedu.address.logic.parser.MembershipStatusPredicate;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.club.Club;
import seedu.address.model.membership.Membership;
import seedu.address.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code ListMemberCommand}.
 */
public class ListMemberCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Predicate<Membership> predicate = new MembershipStatusPredicate();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Club clubMembersToList = model.getFilteredClubList().get(INDEX_FIRST_CLUB.getZeroBased());
        ListMemberCommand listMemberCommand = new ListMemberCommand(INDEX_FIRST_CLUB, predicate);

        String expectedMessage = ListMemberCommand.MESSAGE_SUCCESS;

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updateFilteredClubList(club -> club.equals(clubMembersToList));
        Predicate<Person> isInClub = person -> person.getMemberships().stream().map(Membership::getClub)
                .anyMatch(club -> club.equals(clubMembersToList));
        expectedModel.updateFilteredPersonList(isInClub);

        assertCommandSuccess(listMemberCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredClubList().size() + 1);
        ListMemberCommand listMemberCommand = new ListMemberCommand(outOfBoundIndex, predicate);

        assertCommandFailure(listMemberCommand, model, Messages.MESSAGE_INVALID_CLUB_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showClubAtIndex(model, INDEX_FIRST_CLUB);

        Club clubMembersToList = model.getFilteredClubList().get(INDEX_FIRST_CLUB.getZeroBased());
        ListMemberCommand listMemberCommand = new ListMemberCommand(INDEX_FIRST_CLUB, predicate);

        String expectedMessage = ListMemberCommand.MESSAGE_SUCCESS;

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updateFilteredClubList(club -> club.equals(clubMembersToList));
        Predicate<Person> isInClub = person -> person.getMemberships().stream().map(Membership::getClub)
                .anyMatch(club -> club.equals(clubMembersToList));
        expectedModel.updateFilteredPersonList(isInClub);

        assertCommandSuccess(listMemberCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showClubAtIndex(model, INDEX_FIRST_CLUB);

        Index outOfBoundIndex = INDEX_SECOND_CLUB;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getClubList().size());

        ListMemberCommand listMemberCommand = new ListMemberCommand(outOfBoundIndex, predicate);

        assertCommandFailure(listMemberCommand, model, Messages.MESSAGE_INVALID_CLUB_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        ListMemberCommand listMemberFirstCommand = new ListMemberCommand(INDEX_FIRST_CLUB, predicate);
        ListMemberCommand listMemberSecondCommand = new ListMemberCommand(INDEX_SECOND_CLUB, predicate);

        // same object -> returns true
        assertTrue(listMemberFirstCommand.equals(listMemberFirstCommand));

        // same values -> returns true
        ListMemberCommand listMemberFirstCommandCopy = new ListMemberCommand(INDEX_FIRST_CLUB, predicate);
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
        ListMemberCommand listMemberThirdCommand = new ListMemberCommand(INDEX_FIRST_CLUB, differentPredicate);
        assertFalse(listMemberFirstCommand.equals(listMemberThirdCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        ListMemberCommand listMemberCommand = new ListMemberCommand(targetIndex, predicate);
        String expected = ListMemberCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + ", "
                + "predicate=" + predicate + "}";
        assertEquals(expected, listMemberCommand.toString());
    }

}
