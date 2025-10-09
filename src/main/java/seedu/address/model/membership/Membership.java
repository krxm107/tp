package seedu.address.model.membership;

import seedu.address.model.club.Club;
import seedu.address.model.person.Person;

import java.util.Objects;

public class Membership {

    private final Person person;
    private final Club club;

    public Membership(Person person, Club club) {
        Objects.requireNonNull(person);
        Objects.requireNonNull(club);

        this.person = person;
        this.club = club;
    }

    public Person getPerson() {
        return person;
    }

    public Club getClub() {
        return club;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Membership that = (Membership) o;
        return person.equals(that.person) &&
                club.equals(that.club);
    }

    @Override
    public int hashCode() {
        return Objects.hash(person, club);
    }
}