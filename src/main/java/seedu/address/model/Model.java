package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.club.Club;
import seedu.address.model.membership.Membership;
import seedu.address.model.person.Person;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    Predicate<Club> PREDICATE_SHOW_ALL_CLUBS = unused -> true;

    Predicate<Membership> PREDICATE_SHOW_ALL_MEMBERSHIP = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasPerson(Person person);

    /**
     * Returns true if a club with the same identity as {@code club} exists in the address book.
     */
    boolean hasClub(final Club club);

    boolean hasMembership(Membership membership);
    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deletePerson(Person target);

    /**
     * Deletes the given club.
     * The club must exist in the address book.
     */
    void deleteClub(Club target);

    void deleteMembership(Membership target);

    /**
     * Clears the list of memberships for this club in the address book
     */
    void clearMembership(Club club);

    /**
     * Clears the list of memberships for this club in the address book
     */
    void clearMembership(Person person);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addPerson(Person person);

    /**
     * Adds the given club.
     * {@code club} must not already exist in the address book.
     */
    void addClub(final Club club);

    void addMembership(Membership membership);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void setPerson(Person target, Person editedPerson);

    /**
     * Replaces the given club {@code target} with {@code editedClub}.
     * {@code target} must exist in the address book.
     * The club identity of {@code editedClub} must not be the same as another existing club in the address book.
     */
    void setClub(Club target, Club editedClub);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    ObservableList<Club> getFilteredClubList();

    void updateFilteredClubList(Predicate<Club> predicate);

    ObservableList<Membership> getFilteredMembershipList();

    void updateFilteredMembershipList(Predicate<Membership> predicate);

    void renewMembership(Person personToRenew, Club clubToRenew, int durationInMonths);

    void cancelMembership(Person personToCancel, Club clubToCancel);
}
