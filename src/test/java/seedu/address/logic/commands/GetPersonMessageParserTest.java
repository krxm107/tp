package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.parser.GetPersonMessageParser;
import seedu.address.model.club.Club;
import seedu.address.model.membership.Membership;
import seedu.address.model.person.Person;
import seedu.address.testutil.ClubBuilder;
import seedu.address.testutil.PersonBuilder;

public class GetPersonMessageParserTest {

    private GetPersonMessageParser parser = new GetPersonMessageParser();
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
    public void testGetDefault() {
        assertEquals(Messages.format(person), parser.parse(person, ""));
    }

    @Test
    public void testGetName() {
        assertEquals(person.getName().fullName + " ", parser.parse(person, "n"));
    }

    @Test
    public void testGetPhone() {
        assertEquals(person.getPhone().value + " ", parser.parse(person, "p"));
    }

    @Test
    public void testGetEmail() {
        assertEquals(person.getEmail().value + " ", parser.parse(person, "e"));
    }

    @Test
    public void testGetAddress() {
        assertEquals(person.getAddress().value + " ", parser.parse(person, "a"));
    }

    @Test
    public void testGetMemberships() {
        String expected = person.getMemberships().stream().map(Membership::getClubName)
                .reduce("", (s1, s2) -> s1 + s2 + " ");
        assertEquals(parser.parse(person, "m"), expected);
    }

    @Test
    public void testGetMultiple() {
        assertEquals(parser.parse(person, "abcde"),
                person.getEmail().value + " " + person.getAddress().value + " ");
    }

}
