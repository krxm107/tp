package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Callback;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.club.Club;
import seedu.address.model.field.Address;
import seedu.address.model.field.Email;
import seedu.address.model.field.Name;
import seedu.address.model.field.Phone;
import seedu.address.model.field.Searchable;
import seedu.address.model.membership.Membership;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person implements Searchable {

    // The extractor for the memberships list within this Person
    private static final Callback<Membership, Observable[]> MEMBERSHIP_EXTRACTOR = membership -> new Observable[] {
            membership.statusProperty(),
            membership.expiryDateProperty()
    };

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();
    private final ObservableList<Membership> memberships = FXCollections.observableArrayList(MEMBERSHIP_EXTRACTOR);

    /**
     * Constructs a {@code Person}.
     *
     * @param name    The person's name (required).
     * @param phone   The person's phone number (optional; may be empty).
     * @param email   The person's email address (required).
     * @param address The person's address (optional; may be empty).
     * @param tags    A set of tags (non-null; may be empty).
     *
     *     <p>
     *     If {@code phone} is null, a blank {@code Phone} instance is created.
     *     </p>
     *
     *     <p>
     *     If {@code address} is null, a blank {@code Address} instance is created.
     *     </p>
     */
    public Person(Name name, Phone phone, Email email, Address address, Set<Tag> tags) {
        requireAllNonNull(name, email, tags);
        this.name = name;
        this.phone = (phone == null) ? new Phone("") : phone;
        this.email = email;
        this.address = (address == null) ? new Address("") : address;

        assert tags.size() <= 10;
        assert tags.stream().allMatch(tag -> tag.tagName.length() <= 20);

        this.tags.addAll(tags);
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public boolean phoneHasNonNumericNonSpaceCharacter() {
        return getPhone().containsNonNumericNonSpaceCharacter();
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    public ObservableList<Membership> getMemberships() {
        return this.memberships;
    }

    public boolean checkTagListValidity() {
        return tags != null && tags.stream().allMatch(tag -> tag.isValid()) && tags.size() <= 10;
    }

    /**
     * Clears the list of memberships for this person
     */
    public void clearMemberships() {
        for (Membership m : memberships) {
            m.getClub().removeMember(this);
        }
        memberships.clear();
    }

    /**
     * Returns true if both persons have the same email.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        if (otherPerson == null) {
            return false;
        }

        return this.email.value.equalsIgnoreCase(otherPerson.email.value);
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && address.equals(otherPerson.address)
                && tags.equals(otherPerson.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, phone, email, address, tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("tags", tags)
                .toString();
    }

    public void addMembership(Membership membership) {
        this.memberships.add(membership);
    }

    public void removeMembership(Membership membership) {
        this.memberships.remove(membership);
    }

    /**
     * Removes membership from the person..
     */
    public void removeClub(Club club) {
        // Find the specific membership object to remove
        memberships.stream()
                .filter(m -> m.getClub().equals(club))
                .findFirst()
                .ifPresent(membershipToRemove -> {
                    memberships.remove(membershipToRemove);
                });
        // Also remember to delete membership from ModelManager
    }
}
