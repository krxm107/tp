package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.club.Club;
import seedu.address.model.membership.Membership;
import seedu.address.model.person.Person;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;

    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Club> filteredClubs;
    private final FilteredList<Membership> filteredMemberships;

    /**
     * Initializes a ModelManager with the default values of addressBook and userPrefs.
     */
    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    /**
     * @param addressBook
     *     The dependency-injected addressBook to be managed. It cannot be null.
     *
     * @param userPrefs
     *     The user preferences to be managed. It cannot be null.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        // Update membership status upon initialization
        this.addressBook.updateMembershipStatus();
        this.userPrefs = new UserPrefs(userPrefs);
        this.filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        this.filteredClubs = new FilteredList<>(this.addressBook.getClubList());
        this.filteredMemberships = new FilteredList<>(this.addressBook.getMembershipList());

        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        updateFilteredClubList(PREDICATE_SHOW_ALL_CLUBS);
        updateFilteredMembershipList(PREDICATE_SHOW_ALL_MEMBERSHIP);
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return addressBook.hasPerson(person);
    }

    @Override
    public boolean hasClub(final Club club) {
        requireNonNull(club);
        return addressBook.hasClub(club);
    }

    @Override
    public boolean hasMembership(Membership membership) {
        requireNonNull(membership);
        return addressBook.hasMembership(membership);
    }

    @Override
    public void deletePerson(Person target) {
        addressBook.removePerson(target);
    }

    @Override
    public void deleteClub(Club target) {
        addressBook.removeClub(target);
    }

    @Override
    public void deleteMembership(Membership target) {
        addressBook.removeMembership(target);
    }

    @Override
    public void clearMembership(Club club) {
        for (Membership m : club.getMemberships()) {
            addressBook.removeMembership(m);
        }
    }

    @Override
    public void clearMembership(Person person) {
        for (Membership m : person.getMemberships()) {
            addressBook.removeMembership(m);
        }
    }

    @Override
    public void addPerson(Person person) {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void addClub(final Club club) {
        addressBook.addClub(club);
        updateFilteredClubList(PREDICATE_SHOW_ALL_CLUBS);
    }

    @Override
    public void addMembership(Membership membership) {
        addressBook.addMembership(membership);
        updateFilteredMembershipList(PREDICATE_SHOW_ALL_MEMBERSHIP);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        addressBook.setPerson(target, editedPerson);
    }

    @Override
    public void setClub(Club target, Club editedClub) {
        requireAllNonNull(target, editedClub);

        addressBook.setClub(target, editedClub);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    @Override
    public void sortFilteredPersonList(Comparator<Person> personComparator) {
        addressBook.sortPersonList(personComparator);
    }

    @Override
    public void sortFilteredClubList(Comparator<Club> clubComparator) {
        addressBook.sortClubList(clubComparator);
    }

    //=========== Filtered Club List Accessors =============================================================

    @Override
    public ObservableList<Club> getFilteredClubList() {
        return filteredClubs;
    }

    @Override
    public void updateFilteredClubList(Predicate<Club> predicate) {
        requireNonNull(predicate);
        filteredClubs.setPredicate(predicate);
    }

    //=========== Filtered Membership List Accessors =============================================================

    @Override
    public void renewMembership(Person person, Club club, int durationInMonths) {
        requireAllNonNull(person, club);
        //todo: update exception handling later
        Membership membershipToCheck = new Membership(person, club);
        if (!hasMembership(membershipToCheck)) {
            throw new IllegalArgumentException("Membership does not exist.");
        }
        addressBook.renewMembership(person, club, durationInMonths);
    }

    @Override
    public void cancelMembership(Person person, Club club) {
        requireAllNonNull(person, club);
        //todo: update exception handling later
        Membership membershipToCheck = new Membership(person, club);
        if (!hasMembership(membershipToCheck)) {
            throw new IllegalArgumentException("Membership does not exist.");
        }
        addressBook.cancelMembership(person, club);
    }

    @Override
    public void reactivateMembership(Person person, Club club, int durationInMonths) {
        requireAllNonNull(person, club);
        //todo: update exception handling later
        Membership membershipToCheck = new Membership(person, club);
        if (!hasMembership(membershipToCheck)) {
            throw new IllegalArgumentException("Membership does not exist.");
        }
        addressBook.reactivateMembership(person, club, durationInMonths);
    }

    @Override
    public ObservableList<Membership> getFilteredMembershipList() {
        return filteredMemberships;
    }

    @Override
    public void updateFilteredMembershipList(Predicate<Membership> predicate) {
        requireNonNull(predicate);
        filteredMemberships.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return addressBook.equals(otherModelManager.addressBook)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredPersons.equals(otherModelManager.filteredPersons)
                && filteredClubs.equals(otherModelManager.filteredClubs)
                && filteredMemberships.equals(otherModelManager.filteredMemberships);
    }

}
