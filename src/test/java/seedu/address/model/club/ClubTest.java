package seedu.address.model.club;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalClubs.ARCHERY;
import static seedu.address.testutil.TypicalClubs.BALL;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.ClubBuilder;

public class ClubTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Club club = new ClubBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> club.getTags().remove(0));
    }

    @Test
    public void isSameClub() {
        // same object -> returns true
        assertTrue(ARCHERY.isSameClub(ARCHERY));

        // null -> returns false
        assertFalse(ARCHERY.isSameClub(null));

        // same name, all other attributes different -> returns true
        Club editedArchery = new ClubBuilder(ARCHERY).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ARCHERY.isSameClub(editedArchery));

        // different name, all other attributes same -> returns true
        editedArchery = new ClubBuilder(ARCHERY).withName(VALID_NAME_BOB).build();
        assertTrue(ARCHERY.isSameClub(editedArchery));

        // name differs in case, all other attributes same -> returns true
        Club editedBall = new ClubBuilder(BALL).withName(VALID_NAME_BOB.toLowerCase()).build();
        assertTrue(BALL.isSameClub(editedBall));

        // name has trailing spaces, all other attributes same -> returns true
        String nameWithTrailingSpaces = VALID_NAME_BOB + " ";
        editedBall = new ClubBuilder(BALL).withName(nameWithTrailingSpaces).build();
        assertTrue(BALL.isSameClub(editedBall));

        // same email, all other attributes different -> returns true
        editedArchery = new ClubBuilder(ARCHERY).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ARCHERY.isSameClub(editedArchery));

        // different email, all other attributes same -> returns true
        editedArchery = new ClubBuilder(ARCHERY).withEmail(VALID_EMAIL_BOB).build();
        assertTrue(ARCHERY.isSameClub(editedArchery));

        // email differs in case, all other attributes same -> returns true
        editedBall = new ClubBuilder(BALL).withEmail(VALID_EMAIL_BOB.toLowerCase()).build();
        assertTrue(BALL.isSameClub(editedBall));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Club archeryCopy = new ClubBuilder(ARCHERY).build();
        assertTrue(ARCHERY.equals(archeryCopy));

        // same object -> returns true
        assertTrue(ARCHERY.equals(ARCHERY));

        // null -> returns false
        assertFalse(ARCHERY.equals(null));

        // different type -> returns false
        assertFalse(ARCHERY.equals(5));

        // different club -> returns false
        assertFalse(ARCHERY.equals(BALL));

        // different name -> returns false
        Club editedArchery = new ClubBuilder(ARCHERY).withName(VALID_NAME_BOB).build();
        assertFalse(ARCHERY.equals(editedArchery));

        // different phone -> returns false
        editedArchery = new ClubBuilder(ARCHERY).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ARCHERY.equals(editedArchery));

        // different email -> returns false
        editedArchery = new ClubBuilder(ARCHERY).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ARCHERY.equals(editedArchery));

        // different address -> returns false
        editedArchery = new ClubBuilder(ARCHERY).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(ARCHERY.equals(editedArchery));

        // different tags -> returns false
        editedArchery = new ClubBuilder(ARCHERY).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ARCHERY.equals(editedArchery));
    }

    @Test
    public void toStringMethod() {
        String expected =
                Club.class.getCanonicalName()
                + "{name=" + ARCHERY.getName()
                + ", phone=" + ARCHERY.getPhone()
                + ", email=" + ARCHERY.getEmail() + ", address="
                + ARCHERY.getAddress() + ", tags=" + ARCHERY.getTags() + "}";

        assertEquals(expected, ARCHERY.toString());
    }
}
