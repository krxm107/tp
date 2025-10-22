package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalClubs.ARCHERY;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
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

public final class AddClubCommandTest {

    @Test
    public void constructor_nullClub_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddClubCommand(null));
    }

    @Test
    public void execute_clubAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingClubAdded modelStub = new ModelStubAcceptingClubAdded();
        Club validClub = new ClubBuilder().build();

        CommandResult commandResult = new AddClubCommand(validClub).execute(modelStub);

        assertEquals(String.format(AddClubCommand.MESSAGE_SUCCESS, Messages.format(validClub)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validClub), modelStub.clubsAdded);
    }

    @Test
    public void execute_duplicateClub_throwsCommandException() {
        Club validClub = new ClubBuilder().build();
        AddClubCommand addClubCommand = new AddClubCommand(validClub);
        ModelStub modelStub = new ModelStubWithClub(validClub);

        assertThrows(CommandException.class,
                AddClubCommand.MESSAGE_DUPLICATE_CLUB, () -> addClubCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Club archery = new ClubBuilder().withName("Alice").build();
        Club bob = new ClubBuilder().withName("Bob").build();
        AddClubCommand addAliceCommand = new AddClubCommand(archery);
        AddClubCommand addBobCommand = new AddClubCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddClubCommand addAliceCommandCopy = new AddClubCommand(archery);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different club -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    @Test
    public void toStringMethod() {
        AddClubCommand addClubCommand = new AddClubCommand(ARCHERY);
        String expected = AddClubCommand.class.getCanonicalName() + "{toAdd=" + ARCHERY + "}";
        assertEquals(expected, addClubCommand.toString());
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
        public void setPerson(Person target, Person editedClub) {
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
        public void addClub(Club c) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addMembership(Membership membership) {

        }

        @Override
        public void deleteClub(Club target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteMembership(Membership target) {

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
    }

    /**
     * A Model stub that contains a single club.
     */
    private class ModelStubWithClub extends ModelStub {
        private final Club club;

        ModelStubWithClub(Club club) {
            requireNonNull(club);
            this.club = club;
        }

        @Override
        public boolean hasClub(Club club) {
            requireNonNull(club);
            return this.club.isSameClub(club);
        }
    }

    /**
     * A Model stub that always accept the club being added.
     */
    private class ModelStubAcceptingClubAdded extends ModelStub {
        final ArrayList<Club> clubsAdded = new ArrayList<>();

        @Override
        public boolean hasClub(Club club) {
            requireNonNull(club);
            return clubsAdded.stream().anyMatch(club::isSameClub);
        }

        @Override
        public void addClub(Club club) {
            requireNonNull(club);
            clubsAdded.add(club);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
