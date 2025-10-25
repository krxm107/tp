package seedu.address.logic.search.predicates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.club.Club;
import seedu.address.model.person.Person;
import seedu.address.testutil.ClubBuilder;
import seedu.address.testutil.PersonBuilder;

public class PhoneMatchesPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        PhoneMatchesPredicate<Person> firstPredicate = new PhoneMatchesPredicate<>(firstPredicateKeywordList);
        PhoneMatchesPredicate<Person> secondPredicate = new PhoneMatchesPredicate<>(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PhoneMatchesPredicate<Person> firstPredicateCopy = new PhoneMatchesPredicate<>(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        TagsMatchPredicate<Person> tagPredicate = new TagsMatchPredicate<>(firstPredicateKeywordList);
        assertFalse(firstPredicate.equals(tagPredicate));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different tag -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_phoneContainsKeywords_returnsTrue() {
        PhoneMatchesPredicate<Club> predicate = new PhoneMatchesPredicate<>(Collections.singletonList("52752752"));
        assertTrue(predicate.test(new ClubBuilder().withPhone("52752752").build()));

        // Substring keywords
        predicate = new PhoneMatchesPredicate<>(Arrays.asList("2752"));
        assertTrue(predicate.test(new ClubBuilder().withPhone("5275 2752").build()));
    }

    @Test
    public void test_phoneDoesNotContainKeywords_returnsFalse() {
        // Zero keywords - should not occur if PhoneParser works
        PhoneMatchesPredicate<Person> predicate = new PhoneMatchesPredicate<>(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withPhone("9999 9999").build()));

        // Non-matching keyword
        predicate = new PhoneMatchesPredicate<>(Arrays.asList("1", "9999 9999 9999"));
        assertFalse(predicate.test(new PersonBuilder().withPhone("9999 9999").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        PhoneMatchesPredicate<Person> predicate = new PhoneMatchesPredicate<>(keywords);

        String expected = PhoneMatchesPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
