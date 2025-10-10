package seedu.address.testutil;

import seedu.address.model.AddressBook;

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
        ab.setPersons(TypicalPersons.getTypicalPersons());
        return ab;
    }

}
