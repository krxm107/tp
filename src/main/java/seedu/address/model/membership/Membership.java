package seedu.address.model.membership;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import seedu.address.model.club.Club;
import seedu.address.model.person.Person;

/**
 * Represents a Membership of a Person in a Club.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Membership {

    private final Person person;
    private final Club club;
    private final LocalDate joinDate;
    private LocalDate expiryDate;
    private List<LocalDate> renewalDates;
    private MembershipStatus status;

    /**
     * Every field must be present and not null.
     */
    public Membership(Person person, Club club, LocalDate joinDate) {
        Objects.requireNonNull(person);
        Objects.requireNonNull(club);

        this.person = person;
        this.club = club;
        this.joinDate = joinDate;
    }

    /**
     * Constructor that sets joinDate to current date and role to "member" by default.
     */
    public Membership(Person person, Club club) {
        Objects.requireNonNull(person);
        Objects.requireNonNull(club);
        this.person = person;
        this.club = club;
        this.joinDate = LocalDate.now();
    }

    public Person getPerson() {
        return person;
    }

    public Club getClub() {
        return club;
    }

    public LocalDate getJoinDate() {
        return joinDate;
    }

    public String getClubName() {
        return club.getName().toString();
    }

    public String getPersonName() {
        return person.getName().toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Membership that = (Membership) o;
        return person.equals(that.person)
                && club.equals(that.club);
    }

    @Override
    public int hashCode() {
        return Objects.hash(person, club);
    }
}
