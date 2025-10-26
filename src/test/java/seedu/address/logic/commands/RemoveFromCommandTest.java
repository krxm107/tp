package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalClubs.ARCHERY;
import static seedu.address.testutil.TypicalClubs.BALL;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CLUB;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_CLUB;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.club.Club;
import seedu.address.model.membership.Membership;
import seedu.address.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) and unit tests for RemoveFromCommand.
 */
public class RemoveFromCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        AddressBook ab = new AddressBook();
        ab.addPerson(ALICE);
        ab.addPerson(BENSON);
        ab.addClub(ARCHERY);
        ab.addClub(BALL);
        model = new ModelManager(ab, new UserPrefs());
        expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
    }

    @Test
    public void execute_removeSinglePersonFromSingleClub_success() {
        Person personToRemove = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Club clubToRemoveFrom = model.getFilteredClubList().get(INDEX_FIRST_CLUB.getZeroBased());
        Membership membership = new Membership(personToRemove, clubToRemoveFrom);
        model.addMembership(membership);

        RemoveFromCommand removeFromCommand = new RemoveFromCommand(
                new Index[]{INDEX_FIRST_PERSON}, new Index[]{INDEX_FIRST_CLUB});

        String expectedMessage = String.format(RemoveFromCommand.MESSAGE_REMOVED_FROM_CLUB,
                personToRemove.getName(), clubToRemoveFrom.getName()) + "\n";

        expectedModel.addMembership(membership); // Add to expected model first
        expectedModel.deleteMembership(membership); // Then delete

        assertCommandSuccess(removeFromCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_removeMultiplePersonsFromSingleClub_success() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person secondPerson = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        Club clubToRemoveFrom = model.getFilteredClubList().get(INDEX_FIRST_CLUB.getZeroBased());

        Membership firstMembership = new Membership(firstPerson, clubToRemoveFrom);
        Membership secondMembership = new Membership(secondPerson, clubToRemoveFrom);
        model.addMembership(firstMembership);
        model.addMembership(secondMembership);

        RemoveFromCommand removeFromCommand = new RemoveFromCommand(
                new Index[]{INDEX_FIRST_PERSON, INDEX_SECOND_PERSON}, new Index[]{INDEX_FIRST_CLUB});

        String expectedMessage = String.format(RemoveFromCommand.MESSAGE_REMOVED_FROM_CLUB,
                firstPerson.getName() + ", " + secondPerson.getName(), clubToRemoveFrom.getName()) + "\n";

        expectedModel.addMembership(firstMembership);
        expectedModel.addMembership(secondMembership);
        expectedModel.deleteMembership(firstMembership);
        expectedModel.deleteMembership(secondMembership);

        assertCommandSuccess(removeFromCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_nonExistentMembership_reportsError() {
        Person person = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Club club = model.getFilteredClubList().get(INDEX_FIRST_CLUB.getZeroBased());
        RemoveFromCommand removeFromCommand = new RemoveFromCommand(
                new Index[]{INDEX_FIRST_PERSON}, new Index[]{INDEX_FIRST_CLUB});

        String expectedMessage = String.format(RemoveFromCommand.MESSAGE_NOTEXIST_MEMBERSHIP,
                person.getName(), club.getName()) + "\n";

        assertCommandSuccess(removeFromCommand, model, expectedMessage, model);
    }

    @Test
    public void execute_invalidPersonIndex_reportsError() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        RemoveFromCommand removeFromCommand = new RemoveFromCommand(
                new Index[]{outOfBoundIndex}, new Index[]{INDEX_FIRST_CLUB});

        String expectedMessage = String.format(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX_DETAILED,
                outOfBoundIndex.getOneBased()) + "\n";
        assertCommandSuccess(removeFromCommand, model, expectedMessage, model);
    }

    @Test
    public void execute_invalidClubIndex_reportsError() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredClubList().size() + 1);
        RemoveFromCommand removeFromCommand = new RemoveFromCommand(
                new Index[]{INDEX_FIRST_PERSON}, new Index[]{outOfBoundIndex});

        String expectedMessage = String.format(Messages.MESSAGE_INVALID_CLUB_DISPLAYED_INDEX_DETAILED,
                outOfBoundIndex.getOneBased()) + "\n";
        assertCommandSuccess(removeFromCommand, model, expectedMessage, model);
    }

    @Test
    public void equals() {
        RemoveFromCommand removeFromFirstCommand = new RemoveFromCommand(
                new Index[]{INDEX_FIRST_PERSON}, new Index[]{INDEX_FIRST_CLUB});
        RemoveFromCommand removeFromSecondCommand = new RemoveFromCommand(
                new Index[]{INDEX_SECOND_PERSON}, new Index[]{INDEX_SECOND_CLUB});

        // same object -> returns true
        assertTrue(removeFromFirstCommand.equals(removeFromFirstCommand));

        // same values -> returns true
        RemoveFromCommand removeFromFirstCommandCopy = new RemoveFromCommand(
                new Index[]{INDEX_FIRST_PERSON}, new Index[]{INDEX_FIRST_CLUB});
        assertTrue(removeFromFirstCommand.equals(removeFromFirstCommandCopy));

        // different types -> returns false
        assertFalse(removeFromFirstCommand.equals(1));

        // null -> returns false
        assertFalse(removeFromFirstCommand.equals(null));

        // different command -> returns false
        assertFalse(removeFromFirstCommand.equals(removeFromSecondCommand));
    }
}