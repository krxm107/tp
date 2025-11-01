package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.logic.parser.GetPersonMessageParser.parse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.club.Club;
import seedu.address.model.membership.Membership;
import seedu.address.model.person.Person;
import seedu.address.testutil.ClubBuilder;
import seedu.address.testutil.PersonBuilder;

public class GetPersonMessageParserTest {

    private Person person = new PersonBuilder().withName("Person A").withPhone("8888 1234")
            .withEmail("persona@example.com").withAddress("Clementi Ave 1").build();
    private Club membershipA = new ClubBuilder().withName("A").build();
    private Club membershipB = new ClubBuilder().withName("B").build();

    @BeforeEach
    public void addMembers() {
        membershipA.addMember(person);
        membershipB.addMember(person);
    }

    @Test
    public void testGetName() {
        try {
            assertEquals(person.getName().fullName, parse("n").apply(person));
        } catch (ParseException e) {
            fail();
        }
    }

    @Test
    public void testGetPhone() {
        try {
            assertEquals(person.getPhone().value, parse("p").apply(person));
        } catch (ParseException e) {
            fail();
        }
    }

    @Test
    public void testGetEmail() {
        try {
            assertEquals(person.getEmail().value, parse("e").apply(person));
        } catch (ParseException e) {
            fail();
        }
    }

    @Test
    public void testGetAddress() {
        try {
            assertEquals(person.getAddress().value, parse("a").apply(person));
        } catch (ParseException e) {
            fail();
        }
    }

    @Test
    public void testGetMemberships() {
        String expected = person.getMemberships().stream().map(Membership::getClubName)
                .reduce(person.getName().fullName + ": ", (s1, s2) -> s1 + "\n" + s2);
        try {
            assertEquals(parse("m").apply(person), expected);
        } catch (ParseException e) {
            fail();
        }
    }

}
