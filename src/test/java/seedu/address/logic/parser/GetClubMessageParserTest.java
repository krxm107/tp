package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.logic.parser.GetClubMessageParser.parse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.club.Club;
import seedu.address.model.membership.Membership;
import seedu.address.model.membership.MembershipStatus;
import seedu.address.model.person.Person;
import seedu.address.testutil.ClubBuilder;
import seedu.address.testutil.PersonBuilder;

public class GetClubMessageParserTest {

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
    public void testGetName() {
        try {
            assertEquals(club.getName().fullName, parse("n").apply(club));
        } catch (ParseException e) {
            fail();
        }
    }

    @Test
    public void testGetPhone() {
        try {
            assertEquals(club.getPhone().value, parse("p").apply(club));
        } catch (ParseException e) {
            fail();
        }
    }

    @Test
    public void testGetEmail() {
        try {
            assertEquals(club.getEmail().value, parse("e").apply(club));
        } catch (ParseException e) {
            fail();
        }
    }

    @Test
    public void testGetAddress() {
        try {
            assertEquals(club.getAddress().value, parse("a").apply(club));
        } catch (ParseException e) {
            fail();
        }
    }
    @Test
    public void testGetMembers() {
        String expected = club.getMemberships().stream().map(Membership::getPersonName)
                .reduce(club.getName().fullName + ": ", (s1, s2) -> s1 + "\n" + s2);
        try {
            assertEquals(parse("m").apply(club), expected);
        } catch (ParseException e) {
            fail();
        }
    }

    @Test
    public void testGetFull() {
        String expected = club.getMemberships().stream()
                .filter(membership -> !membership.getStatus().equals(MembershipStatus.CANCELLED))
                .map(Membership::getPerson).map(Messages::format)
                .reduce(Messages.format(club), (s1, s2) -> s1 + "\n" + s2);
        try {
            assertEquals(parse("*").apply(club), expected);
        } catch (ParseException e) {
            fail();
        }
    }

}
