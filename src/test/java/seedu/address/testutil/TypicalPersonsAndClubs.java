package seedu.address.testutil;

import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.club.Club;
import seedu.address.model.membership.Membership;
import seedu.address.model.person.Person;

/**
 * A utility class containing a list of {@code Club} objects and {@code Person} objects to be used in tests.
 */
public class TypicalPersonsAndClubs {

    private TypicalPersonsAndClubs() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons and clubs.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = TypicalClubs.getTypicalAddressBook();
        List<Person> persons = TypicalPersons.getTypicalPersons();
        List<Club> clubs = TypicalClubs.getTypicalClubs();
        Membership m = new Membership(persons.get(0), clubs.get(0), 1);
        persons.get(0).addMembership(m);
        clubs.get(0).addMembership(m);
        ab.setPersons(persons);
        ab.setClubs(clubs);
        ab.addMembership(m);
        return ab;
    }

}
