package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CLUB;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_CLUB;
import static seedu.address.testutil.TypicalPersons.ALICE;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.club.Club;
import seedu.address.model.membership.Membership;
import seedu.address.model.person.Person;
import seedu.address.testutil.ClubBuilder;
import seedu.address.testutil.PersonBuilder;

public class AddPersonCommandTest {

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddPersonCommand(null));
    }

    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        Person validPerson = new PersonBuilder().build();

        CommandResult commandResult = new AddPersonCommand(validPerson).execute(modelStub);

        assertEquals(String.format(AddPersonCommand.MESSAGE_SUCCESS, Messages.format(validPerson)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validPerson), modelStub.personsAdded);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Person validPerson = new PersonBuilder().build();
        AddPersonCommand addPersonCommand = new AddPersonCommand(validPerson);
        ModelStub modelStub = new ModelStubWithPerson(validPerson);

        assertThrows(CommandException.class,
                     AddPersonCommand.MESSAGE_DUPLICATE_PERSON, () -> addPersonCommand.execute(modelStub));
    }

    @Test
    public void execute_addPersonToClub_success() throws CommandException {
        ModelStubWithClub modelStub = new ModelStubWithClub();
        Person person = new PersonBuilder().build();
        AddPersonCommand command = new AddPersonCommand(person, new Index[]{INDEX_FIRST_CLUB});

        command.execute(modelStub);

        assertTrue(modelStub.hasMembership(new Membership(person, modelStub.getFilteredClubList().get(0))));
    }

    @Test
    public void execute_addPersonWithInvalidClubIndex_reportsError() throws CommandException {
        ModelStubWithClub modelStub = new ModelStubWithClub();
        Person person = new PersonBuilder().build();
        AddPersonCommand command = new AddPersonCommand(person, new Index[]{INDEX_SECOND_CLUB}); // Index 2 is invalid

        CommandResult commandResult = command.execute(modelStub);

        String expectedMessage = String.format("New person added: %s", Messages.format(person))
                + "\nMembership addition result:\n"
                + String.format(Messages.MESSAGE_INVALID_CLUB_DISPLAYED_INDEX_DETAILED, INDEX_SECOND_CLUB.getOneBased())
                + System.lineSeparator();

        assertEquals(expectedMessage, commandResult.getFeedbackToUser());
        assertFalse(modelStub.hasMembership(new Membership(person, modelStub.getFilteredClubList().get(0))));
    }

    @Test
    public void execute_addPersonWithMixedClubIndexes_addsValidAndReportsError() throws CommandException {
        ModelStubWithClub modelStub = new ModelStubWithClub();
        Person person = new PersonBuilder().build();
        Club validClub = modelStub.getFilteredClubList().get(0);
        AddPersonCommand command = new AddPersonCommand(person, new Index[]{INDEX_FIRST_CLUB, INDEX_SECOND_CLUB});

        CommandResult commandResult = command.execute(modelStub);

        String expectedMessage = String.format("New person added: %s", Messages.format(person))
                + "\nMembership addition result:\n"
                + String.format(Messages.MESSAGE_INVALID_CLUB_DISPLAYED_INDEX_DETAILED, INDEX_SECOND_CLUB.getOneBased())
                + System.lineSeparator()
                + String.format(AddMembershipCommand.MESSAGE_ADDED_TO_CLUB, person.getName(), validClub.getName());

        assertEquals(expectedMessage, commandResult.getFeedbackToUser());
        assertTrue(modelStub.hasMembership(new Membership(person, validClub)));
    }

    @Test
    public void equals() {
        Person alice = new PersonBuilder().withName("Alice").build();
        Person bob = new PersonBuilder().withName("Bob").build();
        AddPersonCommand addAliceCommand = new AddPersonCommand(alice);
        AddPersonCommand addBobCommand = new AddPersonCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddPersonCommand addAliceCommandCopy = new AddPersonCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different person -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    @Test
    public void toStringMethod() {
        AddPersonCommand addPersonCommand = new AddPersonCommand(ALICE);
        String expected = AddPersonCommand.class.getCanonicalName() + "{toAdd=" + ALICE + "}";
        assertEquals(expected, addPersonCommand.toString());
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setClub(Club target, Club editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void sortFilteredPersonList(Comparator<Person> personComparator) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addClub(Club c) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addMembership(Membership membership) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteClub(Club target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteMembership(Membership target) {

        }

        @Override
        public void clearMembership(Club club) {

        }

        @Override
        public void clearMembership(Person person) {

        }

        @Override
        public boolean hasClub(Club c) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasMembership(Membership membership) {
            return false;
        }

        @Override
        public ObservableList<Club> getFilteredClubList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredClubList(Predicate<Club> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void sortFilteredClubList(Comparator<Club> clubComparator) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Membership> getFilteredMembershipList() {
            return null;
        }

        @Override
        public void updateFilteredMembershipList(Predicate<Membership> predicate) {

        }

        @Override
        public void renewMembership(Person personToRenew, Club clubToRenew, int durationInMonths) {
        }

        @Override
        public void cancelMembership(Person personToCancel, Club clubToCancel) {
        }

        @Override
        public void reactivateMembership(Person personToReactivate, Club clubToReactivate, int durationInMonths) {

        }
    }

    /**
     * A Model stub that contains a single person.
     */
    private class ModelStubWithPerson extends ModelStub {
        private final Person person;

        ModelStubWithPerson(Person person) {
            requireNonNull(person);
            this.person = person;
        }

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return this.person.isSamePerson(person);
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingPersonAdded extends ModelStub {
        final ArrayList<Person> personsAdded = new ArrayList<>();

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return personsAdded.stream().anyMatch(person::isSamePerson);
        }

        @Override
        public void addPerson(Person person) {
            requireNonNull(person);
            personsAdded.add(person);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

    /**
     * A Model stub that contains a single club and accepts person additions.
     */
    private class ModelStubWithClub extends ModelStubAcceptingPersonAdded {
        private final Club club = new ClubBuilder().build();
        private final List<Membership> memberships = new ArrayList<>();

        @Override
        public ObservableList<Club> getFilteredClubList() {
            return FXCollections.observableArrayList(club);
        }

        @Override
        public void addMembership(Membership membership) {
            memberships.add(membership);
        }

        @Override
        public boolean hasMembership(Membership membership) {
            return memberships.contains(membership);
        }
    }
}
