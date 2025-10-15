package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.club.Club;
import seedu.address.model.field.Email;
import seedu.address.model.field.Name;
import seedu.address.model.person.Person;

import java.util.Optional;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Person> getPersonList();

    ObservableList<Club> getClubList();

    Optional<Person> getPersonByName(Name name);

    Optional<Person> getPersonByEmail(Email email);

    Optional<Club> getClubByName(Name name);
}
