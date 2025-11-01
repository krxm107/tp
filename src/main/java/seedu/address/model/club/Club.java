package seedu.address.model.club;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.field.Address;
import seedu.address.model.field.Email;
import seedu.address.model.field.Name;
import seedu.address.model.field.Phone;
import seedu.address.model.field.Searchable;
import seedu.address.model.membership.Membership;
import seedu.address.model.membership.MembershipStatus;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * Represents a Club in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Club implements Searchable {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();
    private final ObservableList<Membership> memberships = FXCollections.observableArrayList();

    /**
     * Constructs a {@code Club}.
     *
     * @param name    The club's name (required).
     * @param phone   The club's phone number (optional; may be empty).
     * @param email   The club's email address (required).
     * @param address The club's address (optional; maybe be empty).
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
    public Club(Name name, Phone phone, Email email, Address address, Set<Tag> tags) {
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

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    public boolean checkTagListValidity() {
        return tags != null && tags.stream().allMatch(tag -> tag.isValid()) && tags.size() <= 10;
    }

    /**
     * Returns true if both clubs have the same name and / or the same email.
     * This defines a weaker notion of equality between two clubs.
     */
    public boolean isSameClub(Club otherClub) {
        if (otherClub == this) {
            return true;
        }

        if (otherClub == null) {
            return false;
        }

        return name.fullName.equalsIgnoreCase(otherClub.name.fullName)
                || email.value.equalsIgnoreCase(otherClub.email.value);
    }

    public boolean addMembership(Membership membership) {
        return memberships.add(membership);
    }

    public boolean phoneHasNonNumericNonSpaceCharacter() {
        return getPhone().containsNonNumericNonSpaceCharacter();
    }

    /**
     * Adds a person as a member of the club.
     *
     * @param person The person to be added as a member.
     * @return true if the person was added successfully, false if they were already a member.
     */
    public boolean addMember(Person person) {
        Membership newMembership = new Membership(person, this);

        boolean added = this.memberships.add(newMembership);

        // If added successfully, update the person object as well
        if (added) {
            person.addMembership(newMembership);
        }
        return added;
    }

    /**
     * Checks if a person is a member of the club.
     *
     * @param person The person to check for membership.
     * @return true if the person is a member, false otherwise.
     */
    public boolean isMember(Person person) {
        return memberships.stream()
                .anyMatch(membership -> membership.getPerson().equals(person));
    }

    /**
     * Removes membership of a person from the club
     */
    public void removeMember(Person person) {
        // Find the specific membership object to remove
        memberships.stream()
                .filter(m -> m.getPerson().equals(person))
                .findFirst()
                .ifPresent(membershipToRemove -> {
                    memberships.remove(membershipToRemove);
                });
        // Also remember to delete membership from ModelManager
    }

    /**
     * Returns the observable set of memberships for this club including all statuses
     */
    public ObservableList<Membership> getMemberships() {
        return this.memberships;
    }

    /**
     * Returns the number of members in this club including pending cancellation membership
     * and exclude cancelled membership.
     */
    public int getMemberCount() {
        return (int) memberships.stream()
                .filter(membership -> membership.getStatus() != MembershipStatus.CANCELLED)
                .count();
    }

    /**
     * Clears the list of members for this club
     */
    public void clearMembers() {
        for (Membership m : memberships) {
            m.getPerson().removeMembership(m);
        }
        memberships.clear();
    }

    /**
     * Returns true if both clubs have the same identity and data fields.
     * This defines a stronger notion of equality between two clubs.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Club)) {
            return false;
        }

        Club otherClub = (Club) other;
        return name.equals(otherClub.name)
                && phone.equals(otherClub.phone)
                && email.equals(otherClub.email)
                && address.equals(otherClub.address)
                && tags.equals(otherClub.tags);
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

}
