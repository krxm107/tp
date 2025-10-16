package seedu.address.model;

import java.util.Optional;

import javafx.collections.ObservableList;
import seedu.address.model.club.Club;
import seedu.address.model.field.Email;
import seedu.address.model.field.Name;
import seedu.address.model.membership.Membership;
import seedu.address.model.person.Person;


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

    ObservableList<Membership> getMembershipList();

    Optional<Person> getPersonByName(Name name);

    Optional<Person> getPersonByEmail(Email email);

    Optional<Club> getClubByName(Name name);
}
