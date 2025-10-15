package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.club.Club;
import seedu.address.model.club.UniqueClubList;
import seedu.address.model.field.Email;
import seedu.address.model.field.Name;
import seedu.address.model.membership.Membership;
import seedu.address.model.membership.UniqueMembershipList;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.util.SampleDataUtil;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {
    private final UniquePersonList persons;
    private final UniqueClubList clubs;
    private final UniqueMembershipList memberships;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();
        clubs = new UniqueClubList();
        memberships = new UniqueMembershipList();
    }

    public AddressBook() {

    }

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Person> persons) {
        this.persons.setPersons(persons);
    }

    /**
     * Replaces the contents of the club list with {@code club}.
     * {@code club} must not contain duplicate clubs.
     */
    public void setClubs(List<Club> clubs) {
        this.clubs.setClubs(clubs);
    }

    public void setMemberships(List<Membership> memberships) {
        this.memberships.setMemberships(memberships);
    }
    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
        setClubs(newData.getClubList());
        setMemberships(newData.getMembershipList());

        // resetToDummyData();
    }

    // This method is for private testing purposes, do not remove.
    private void resetToDummyData() {
        setPersons(SampleDataUtil.getSampleAddressBook().getPersonList());
        setClubs(SampleDataUtil.getSampleAddressBook().getClubList());
        setMemberships(SampleDataUtil.getSampleAddressBook().getMembershipList());
    }

    //// club-level operations

    /**
     * Returns true if a person with the same identity as {@code club} exists in the address book.
     */
    public boolean hasClub(Club club) {
        requireNonNull(club);
        return clubs.contains(club);
    }

    /**
     * Adds a club to the address book.
     * The club must not already exist in the address book.
     */
    public void addClub(Club club) {
        clubs.add(club);
    }

    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return persons.contains(person);
    }

    /**
     * Adds a person to the address book.
     * The person must not already exist in the address book.
     */
    public void addPerson(Person p) {
        persons.add(p);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    public void setPerson(Person target, Person editedPerson) {
        requireNonNull(editedPerson);

        persons.setPerson(target, editedPerson);
    }

    //// membership-level operation

    public boolean hasMembership(Membership membership) {
        requireNonNull(membership);
        return memberships.contains(membership);
    }

    public void addMembership(Membership membership) {
        memberships.add(membership);
    }

    // get person by name directly from addressbook
    @Override
    public Optional<Person> getPersonByName(Name target) {
        requireNonNull(target);
        return persons.getPersonByName(target);
    }

    @Override
    public Optional<Person> getPersonByEmail(Email email) {
        requireNonNull(email);
        return persons.getPersonByEmail(email); // Delegate this call to UniquePersonList
    }

    @Override
    public Optional<Club> getClubByName(Name target) {
        requireNonNull(target);
        return clubs.getClub(target);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removePerson(Person key) {
        persons.remove(key);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeClub(Club key) {
        clubs.remove(key);
    }

    public void removeMembership(Membership key) {
        memberships.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("persons", persons)
                .toString();
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Club> getClubList() {
        return clubs.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Membership> getMembershipList() {
        return memberships.asUnmodifiableObservableList();
    }


    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddressBook)) {
            return false;
        }

        AddressBook otherAddressBook = (AddressBook) other;
        return persons.equals(otherAddressBook.persons)
                && clubs.equals(otherAddressBook.clubs)
                && memberships.equals(otherAddressBook.memberships);
    }

    @Override
    public int hashCode() {
        return Objects.hash(persons, clubs, memberships);
    }
}
