package seedu.address.model.membership;

import seedu.address.model.club.Club;
import seedu.address.model.person.Person;

import java.time.LocalDate;
import java.util.Objects;

public class Membership {

    private final Person person;
    private final Club club;
    private final LocalDate joinDate;
    private String role;

    public Membership(Person person, Club club, LocalDate joinDate, String role) {
        Objects.requireNonNull(person);
        Objects.requireNonNull(club);

        this.person = person;
        this.club = club;
        this.joinDate = joinDate;
        this.role = role;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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