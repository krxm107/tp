package seedu.address.storage;

import static seedu.address.logic.commands.AddClubCommand.MESSAGE_DUPLICATE_CLUB;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.club.Club;
import seedu.address.model.field.Email;
import seedu.address.model.field.Name;
import seedu.address.model.membership.Membership;
import seedu.address.model.person.Person;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";
    public static final String MESSAGE_INVALID_MEMBERSHIP_LINK =
            "Data file contains an invalid membership link: A person or club could not be found.";

    private final List<JsonAdaptedPerson> persons = new ArrayList<>();
    private final List<JsonAdaptedClub> clubs = new ArrayList<>();
    private final List<JsonAdaptedMembership> memberships = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given persons.
     */
    @JsonCreator
    public JsonSerializableAddressBook(@JsonProperty("persons") List<JsonAdaptedPerson> persons,
                                       @JsonProperty("clubs") List<JsonAdaptedClub> clubs,
                                       @JsonProperty("memberships") List<JsonAdaptedMembership> memberships) {
        if (persons != null) {
            this.persons.addAll(persons);
        }

        if (clubs != null) {
            this.clubs.addAll(clubs);
        }

        if (memberships != null) {
            this.memberships.addAll(memberships);
        }
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableAddressBook(ReadOnlyAddressBook source) {
        persons.addAll(source.getPersonList().stream().map(JsonAdaptedPerson::new).collect(Collectors.toList()));
        clubs.addAll(source.getClubList().stream().map(JsonAdaptedClub::new).collect(Collectors.toList()));

        Set<Membership> allMemberships = source.getMembershipList().stream().collect(Collectors.toSet());

        memberships.addAll(allMemberships.stream()
                .map(JsonAdaptedMembership::new)
                .toList());
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public AddressBook toModelType() throws IllegalValueException {
        AddressBook addressBook = new AddressBook();
        for (JsonAdaptedPerson jsonAdaptedPerson : persons) {
            Person person = jsonAdaptedPerson.toModelType();
            if (addressBook.hasPerson(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            addressBook.addPerson(person);
        }

        for (final JsonAdaptedClub jsonAdaptedClub : clubs) {
            final Club club = jsonAdaptedClub.toModelType();
            if (addressBook.hasClub(club)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_CLUB);
            }
            addressBook.addClub(club);
        }

        // Re-link persons and clubs using the membership data
        for (final JsonAdaptedMembership jsonAdaptedMembership : memberships) {
            // Find the already-loaded Person and Club objects from the address book
            final Person person = addressBook.getPersonByEmail(new Email(jsonAdaptedMembership.getPersonEmail())).get();
            final Club club = addressBook.getClubByName(new Name(jsonAdaptedMembership.getClubName())).get();

            // Use the model's own logic to create the membership link.
            Membership membership = new Membership(person, club, jsonAdaptedMembership.getJoinDate(),
                    jsonAdaptedMembership.getExpiryDate(), jsonAdaptedMembership.getMembershipEventHistory(),
                    jsonAdaptedMembership.getStatus());
            club.addMembership(membership);
            person.addMembership(membership);
            addressBook.addMembership(membership);
        }
        return addressBook;
    }
}
