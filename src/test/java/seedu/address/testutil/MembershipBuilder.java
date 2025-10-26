package seedu.address.testutil;

import seedu.address.model.club.Club;
import seedu.address.model.membership.Membership;
import seedu.address.model.person.Person;

/**
 * A utility class to help with building Membership objects.
 */
public class MembershipBuilder {

    private Person person;
    private Club club;

    /**
     * Creates a {@code MembershipBuilder} with the default details.
     */
    public MembershipBuilder() {
        person = new PersonBuilder().build();
        club = new ClubBuilder().build();
    }

    /**
     * Initializes the MembershipBuilder with the data of {@code membershipToCopy}.
     */
    public MembershipBuilder(Membership membershipToCopy) {
        person = membershipToCopy.getPerson();
        club = membershipToCopy.getClub();
    }

    /**
     * Sets the {@code Person} of the {@code Membership} that we are building.
     */
    public MembershipBuilder withPerson(String name) {
        this.person = new PersonBuilder().withName(name).build();
        return this;
    }

    /**
     * Sets the {@code Club} of the {@code Membership} that we are building.
     */
    public MembershipBuilder withClub(String name) {
        this.club = new ClubBuilder().withName(name).build();
        return this;
    }

    public Membership build() {
        return new Membership(person, club);
    }
}
