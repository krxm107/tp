package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.club.Club;
import seedu.address.model.membership.Membership;
import seedu.address.model.membership.MembershipStatus;
import seedu.address.model.person.Person;
import seedu.address.testutil.ClubBuilder;
import seedu.address.testutil.PersonBuilder;

public class GetClubMessageParserTest {

    private GetClubMessageParser parser = new GetClubMessageParser();
    private Club club = new ClubBuilder().withName("Club A").withPhone("8888 1234")
            .withEmail("cluba@example.com").withAddress("Clementi Ave 1").build();
    private Person memberA = new PersonBuilder().withName("A").build();
    private Person memberB = new PersonBuilder().withName("B").build();

    @BeforeEach
    public void addMembers() {
        club.addMember(memberA);
        club.addMember(memberB);
    }

    @Test
    public void testGetDefault() {
        assertEquals(Messages.format(club), parser.parse(club, ""));
    }

    @Test
    public void testGetName() {
        assertEquals(club.getName().fullName + " ", parser.parse(club, "n"));
    }

    @Test
    public void testGetPhone() {
        assertEquals(club.getPhone().value + " ", parser.parse(club, "p"));
    }

    @Test
    public void testGetEmail() {
        assertEquals(club.getEmail().value + " ", parser.parse(club, "e"));
    }

    @Test
    public void testGetAddress() {
        assertEquals(club.getAddress().value + " ", parser.parse(club, "a"));
    }

    @Test
    public void testGetMembers() {
        String expected = club.getMemberships().stream().map(Membership::getPersonName)
                .reduce("", (s1, s2) -> s1 + s2 + " ");
        assertEquals(parser.parse(club, "m"), expected);
    }

    @Test
    public void testGetMultiple() {
        assertEquals(parser.parse(club, "abcde"),
                club.getEmail().value + " " + club.getAddress().value + " ");
    }

    @Test
    public void testGetFull() {
        String expected = club.getMemberships().stream()
                .filter(membership -> !membership.getStatus().equals(MembershipStatus.CANCELLED))
                .map(Membership::getPerson).map(Messages::format)
                .reduce(Messages.format(club), (s1, s2) -> s1 + "\n" + s2);
        assertEquals(parser.parse(club, "***"), expected);
    }

}
