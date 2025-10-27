package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
 * Contains integration tests (interaction with the Model) and unit tests for AddMembershipCommand.
 */
public class AddMembershipCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        // Use a fresh AddressBook with TypicalPersons and TypicalClubs for complete test isolation
        AddressBook ab = new AddressBook();
        ab.addPerson(ALICE);
        ab.addPerson(BENSON);
        ab.addClub(ARCHERY);
        ab.addClub(BALL);

        model = new ModelManager(ab, new UserPrefs());
    }

    @Test
    public void execute_addSinglePersonToSingleClub_success() {
        Person personToAdd = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Club clubToAddTo = model.getFilteredClubList().get(INDEX_FIRST_CLUB.getZeroBased());
        AddMembershipCommand addMembershipCommand = new AddMembershipCommand(
                new Index[]{INDEX_FIRST_PERSON}, new Index[]{INDEX_FIRST_CLUB});

        String expectedMessage = String.format(
                AddMembershipCommand.MESSAGE_ADDED_TO_CLUB, personToAdd.getName(), clubToAddTo.getName()) + "\n";

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        Person expectedPerson = expectedModel.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Club expectedClub = expectedModel.getFilteredClubList().get(INDEX_FIRST_CLUB.getZeroBased());
        expectedModel.addMembership(new Membership(expectedPerson, expectedClub));

        assertCommandSuccess(addMembershipCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_addMultiplePersonsToSingleClub_success() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person secondPerson = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        Club clubToAddTo = model.getFilteredClubList().get(INDEX_FIRST_CLUB.getZeroBased());
        AddMembershipCommand addMembershipCommand = new AddMembershipCommand(
                new Index[]{INDEX_FIRST_PERSON, INDEX_SECOND_PERSON}, new Index[]{INDEX_FIRST_CLUB});

        String expectedMessage = String.format(AddMembershipCommand.MESSAGE_ADDED_TO_CLUB,
                firstPerson.getName() + ", " + secondPerson.getName(), clubToAddTo.getName()) + "\n";

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        Person expectedFirstPerson = expectedModel.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person expectedSecondPerson = expectedModel.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        Club expectedClub = expectedModel.getFilteredClubList().get(INDEX_FIRST_CLUB.getZeroBased());
        expectedModel.addMembership(new Membership(expectedFirstPerson, expectedClub));
        expectedModel.addMembership(new Membership(expectedSecondPerson, expectedClub));

        assertCommandSuccess(addMembershipCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateMembership_failure() {
        Person personToAdd = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Club clubToAddTo = model.getFilteredClubList().get(INDEX_FIRST_CLUB.getZeroBased());
        model.addMembership(new Membership(personToAdd, clubToAddTo));

        AddMembershipCommand addMembershipCommand =
                new AddMembershipCommand(new Index[]{INDEX_FIRST_PERSON}, new Index[]{INDEX_FIRST_CLUB});
        String expectedMessage = String.format(AddMembershipCommand.MESSAGE_DUPLICATE_MEMBERSHIP,
                personToAdd.getName(), clubToAddTo.getName()) + "\n";

        assertCommandSuccess(addMembershipCommand, model, expectedMessage, model);
    }

    @Test
    public void execute_invalidPersonIndex_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        AddMembershipCommand addMembershipCommand =
                new AddMembershipCommand(new Index[]{outOfBoundIndex}, new Index[]{INDEX_FIRST_CLUB});

        String expectedMessage = String.format(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX_DETAILED,
                outOfBoundIndex.getOneBased()) + "\n";
        assertCommandSuccess(addMembershipCommand, model, expectedMessage, model);
    }

    @Test
    public void execute_invalidClubIndex_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredClubList().size() + 1);
        AddMembershipCommand addMembershipCommand =
                new AddMembershipCommand(new Index[]{INDEX_FIRST_PERSON}, new Index[]{outOfBoundIndex});

        String expectedMessage = String.format(Messages.MESSAGE_INVALID_CLUB_DISPLAYED_INDEX_DETAILED,
                outOfBoundIndex.getOneBased()) + "\n";
        assertCommandSuccess(addMembershipCommand, model, expectedMessage, model);
    }

    @Test
    public void equals() {
        AddMembershipCommand addToFirstCommand = new AddMembershipCommand(
                new Index[]{INDEX_FIRST_PERSON}, new Index[]{INDEX_FIRST_CLUB});
        AddMembershipCommand addToSecondCommand = new AddMembershipCommand(
                new Index[]{INDEX_SECOND_PERSON}, new Index[]{INDEX_SECOND_CLUB});

        // same object -> returns true
        assertEquals(addToFirstCommand, addToFirstCommand);

        // same values -> returns true
        AddMembershipCommand addToFirstCommandCopy = new AddMembershipCommand(
                new Index[]{INDEX_FIRST_PERSON}, new Index[]{INDEX_FIRST_CLUB});
        assertEquals(addToFirstCommand, addToFirstCommandCopy);

        // null -> returns false
        assertNotNull(addToFirstCommand);

        // different command -> returns false
        assertNotEquals(addToFirstCommand, addToSecondCommand);
    }
}
