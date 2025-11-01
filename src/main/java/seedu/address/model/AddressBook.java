package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.LogsCenter;
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

    private final Logger logger = LogsCenter.getLogger(AddressBook.class);

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

        if (!validatePersonList(newData.getPersonList()) || !validateClubList(newData.getClubList()) ) {
            clearAllData();
        }
        
        // resetToDummyData();
    }

    private void clearAllData() {
        setPersons(List.of());
        setClubs(List.of());
        setMemberships(List.of());
    }

    private boolean validatePersonList(List<Person> personList) {
        return personList.stream().allMatch(Person::hasValidTagList);
    }

    private boolean validateClubList(List<Club> clubList) {
        return clubList.stream().allMatch(Club::hasValidTagList);
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

        if (target.equals(editedPerson)) {
            return;
        }

        List<Membership> owned = memberships.asUnmodifiableObservableList().stream()
                .filter(m -> m.getPerson().equals(target))
                .toList();

        // For each old membership, create an equivalent that points to editedPerson
        for (Membership oldM : owned) {
            Membership newM = new Membership(
                    editedPerson,
                    oldM.getClub(),
                    oldM.getJoinDate(),
                    oldM.getExpiryDate(),
                    new ArrayList<>(oldM.getMembershipEventHistory()),
                    oldM.getStatus()
            );

            memberships.setMembership(oldM, newM);
            oldM.getClub().removeMember(target); // removes oldM
            oldM.getClub().addMembership(newM); // add the rebuilt membership to the club

            // Attach the rebuilt membership to the edited person
            editedPerson.removeClub(newM.getClub());
            editedPerson.addMembership(newM);

            // Detach the old membership object from the old person
            target.removeMembership(oldM);
        }

        // Finally, replace the Person in the person list
        persons.setPerson(target, editedPerson);
    }


    /**
     * Replaces the given club {@code target} in the list with {@code editedClub}.
     * {@code target} must exist in the address book.
     * The club identity of {@code editedClub} must not be the same as another existing club in the address book.
     */
    public void setClub(Club target, Club editedClub) {
        requireNonNull(editedClub);

        if (target.equals(editedClub)) {
            return;
        }

        List<Membership> owned = memberships.asUnmodifiableObservableList().stream()
                .filter(m -> m.getClub().equals(target))
                .toList();

        // For each old membership, create an equivalent that points to editedClub
        for (Membership oldM : owned) {
            Membership newM = new Membership(
                    oldM.getPerson(),
                    editedClub,
                    oldM.getJoinDate(),
                    oldM.getExpiryDate(),
                    new ArrayList<>(oldM.getMembershipEventHistory()),
                    oldM.getStatus()
            );

            memberships.setMembership(oldM, newM);


            oldM.getClub().addMembership(newM); // add the rebuilt membership to the club

            oldM.getPerson().removeClub(target); // removes oldM
            target.removeMember(oldM.getPerson()); // removes oldM link on the club side

            // Attach rebuilt membership to both sides
            newM.getPerson().addMembership(newM);
            editedClub.addMembership(newM);
        }

        // Finally, replace the Club in the club list
        clubs.setClub(target, editedClub);
    }


    //// membership-level operation

    /**
     * Returns true if a membership with the same identity as {@code membership} exists in the address book.
     */
    public boolean hasMembership(Membership membership) {
        requireNonNull(membership);
        return memberships.contains(membership);
    }

    public void addMembership(Membership membership) {
        memberships.add(membership);
    }

    @Override
    public Optional<Person> getPersonByEmail(Email email) {
        requireNonNull(email);
        return persons.getPersonByEmail(email); // Delegate this call to UniquePersonList
    }

    //todo: handle exception when membership not found
    /**
     * Renews the membership of a person in a club for the specified duration.
     *
     * @param person The person whose membership is to be renewed.
     * @param club The club for which the membership is to be renewed.
     * @param durationInMonths The duration in months for which the membership is to be renewed.
     */
    public void renewMembership(Person person, Club club, int durationInMonths) {
        Membership membership = memberships.getMembershipByPersonClub(person, club).get();
        membership.renew(durationInMonths);
    }

    /**
     * Cancels the membership of a person in a club.
     *
     * @param person The person whose membership is to be cancelled.
     * @param club The club for which the membership is to be cancelled.
     */
    public void cancelMembership(Person person, Club club) {
        Membership membership = memberships.getMembershipByPersonClub(person, club).get();
        membership.cancel();
    }

    /**
     * Reactivates a cancelled membership of a person in a club for the specified duration.
     *
     * @param person The person whose membership is to be reactivated.
     * @param club The club for which the membership is to be reactivated.
     * @param durationInMonths The duration in months for which the membership is to be reactivated.
     */
    public void reactivateMembership(Person person, Club club, int durationInMonths) {
        Membership membership = memberships.getMembershipByPersonClub(person, club).get();
        membership.reactivate(durationInMonths);
    }

    /**
     * This method should be run once per day to update the status
     * of all memberships in the system.
     */
    public void updateMembershipStatus() {
        // todo: use a logger instead of System.out.println
        logger.info("Performing membership status update...");
        for (Membership m : memberships) {
            m.updateStatus();
        }
        logger.info("Membership status update completed.");
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

    public void sortPersonList(Comparator<Person> personComparator) {
        persons.sort(personComparator);
    }

    public void sortClubList(Comparator<Club> clubComparator) {
        clubs.sort(clubComparator);
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
