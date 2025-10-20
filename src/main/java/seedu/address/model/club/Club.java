package seedu.address.model.club;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.field.Address;
import seedu.address.model.field.Email;
import seedu.address.model.field.Name;
import seedu.address.model.field.Phone;
import seedu.address.model.membership.Membership;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * Represents a Club in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Club {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();
    private final Set<Membership> memberships = new HashSet<>();

    /**
     * Constructs a {@code Person}.
     *
     * @param name    The person's name (required).
     * @param phone   The person's phone number (optional; may be empty).
     * @param email   The person's email address (required).
     * @param address The person's address (optional; maybe be empty).
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

    /**
     * Returns true if both clubs have the same name.
     * This defines a weaker notion of equality between two clubs.
     */
    public boolean isSameClub(Club otherClub) {
        if (otherClub == this) {
            return true;
        }

        return otherClub != null
                && otherClub.getName().equals(getName());
    }

    public boolean addMembership(Membership membership) {
        return memberships.add(membership);
    }
    /**
     * Returns an immutable membership set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public boolean addMember(Person person, String role) {
        Membership newMembership = new Membership(person, this, LocalDate.now(), role);

        boolean added = this.memberships.add(newMembership);

        // If added successfully, update the person object as well
        if (added) {
            person.addMembership(newMembership);
        }
        return added;
    }

    /**
     * Adds a member to the club with a specified join date.
     * This is useful for adding historical members.
     *
     * @param person   The person to be added as a member.
     * @param role     The role of the member in the club.
     * @param joinDate The date the member joined the club.
     * @return true if the member was added successfully, false if they were already a member.
     */
    public boolean addMember(Person person, String role, LocalDate joinDate) {
        Membership newMembership = new Membership(person, this, joinDate, role);
        boolean added = this.memberships.add(newMembership);
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
     * Returns an immutable membership set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public void removeMember(Person person) {
        // Find the specific membership object to remove
        memberships.stream()
                .filter(m -> m.getPerson().equals(person))
                .findFirst()
                .ifPresent(membershipToRemove -> {
                    memberships.remove(membershipToRemove);
                    person.removeMembership(membershipToRemove); // Maintain bidirectional link
                });
    }

    public Set<Membership> getMemberships() {
        return Collections.unmodifiableSet(memberships);
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
        // use this method for custom fields hashing instead of implementing your own
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
