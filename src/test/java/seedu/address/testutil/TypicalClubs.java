package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_ART;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOOKS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_ART;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOOKS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_ART;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOOKS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_ART;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOOKS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_BIG;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_CASUAL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.club.Club;

/**
 * A utility class containing a list of {@code Club} objects to be used in tests.
 */
public class TypicalClubs {

    public static final Club ARCHERY = new ClubBuilder().withName("Archery Club")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("archery@example.com")
            .withPhone("94351253")
            .withTags("sports").build();
    public static final Club BALL = new ClubBuilder().withName("Balls Club")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("ball@example.com").withPhone("98765432")
            .withTags("sports", "casual").build();
    public static final Club CHESS = new ClubBuilder().withName("Chess Club").withPhone("95352563")
            .withEmail("chess@example.com").withAddress("wall street").build();
    public static final Club DANCE = new ClubBuilder().withName("Dance Club").withPhone("87652533")
            .withEmail("dance@example.com").withAddress("10th street").withTags("friends").build();
    public static final Club ENGLISH = new ClubBuilder().withName("English Club").withPhone("9482224")
            .withEmail("english@example.com").withAddress("michegan ave").build();
    public static final Club FRENCH = new ClubBuilder().withName("French Club").withPhone("9482427")
            .withEmail("french@example.com").withAddress("little tokyo").build();
    public static final Club GAME = new ClubBuilder().withName("Games Club").withPhone("9482442")
            .withEmail("game@example.com").withAddress("4th street").build();

    // Manually added - Club's details found in {@code CommandTestUtil}
    public static final Club ART = new ClubBuilder().withName(VALID_NAME_ART).withPhone(VALID_PHONE_ART)
            .withEmail(VALID_EMAIL_ART).withAddress(VALID_ADDRESS_ART).withTags(VALID_TAG_CASUAL).build();
    public static final Club BOOKS = new ClubBuilder().withName(VALID_NAME_BOOKS).withPhone(VALID_PHONE_BOOKS)
            .withEmail(VALID_EMAIL_BOOKS).withAddress(VALID_ADDRESS_BOOKS).withTags(VALID_TAG_BIG, VALID_TAG_CASUAL)
            .build();

    private TypicalClubs() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical clubs.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Club club : getTypicalClubs()) {
            ab.addClub(club);
        }
        return ab;
    }

    public static List<Club> getTypicalClubs() {
        return new ArrayList<>(Arrays.asList(ARCHERY, BALL, CHESS, DANCE, ENGLISH, FRENCH, GAME));
    }
}
