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

public class NameMatchesPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        NameMatchesPredicate<Person> firstPredicate = new NameMatchesPredicate<>(firstPredicateKeywordList);
        NameMatchesPredicate<Person> secondPredicate = new NameMatchesPredicate<>(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        NameMatchesPredicate<Person> firstPredicateCopy = new NameMatchesPredicate<>(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        PhoneMatchesPredicate<Person> phonePredicate = new PhoneMatchesPredicate<>(firstPredicateKeywordList);
        assertFalse(firstPredicate.equals(phonePredicate));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different tag -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        NameMatchesPredicate<Person> predicate = new NameMatchesPredicate<>(Collections.singletonList("john"));
        assertTrue(predicate.test(new PersonBuilder().withName("John").build()));

        // Substring keywords
        predicate = new NameMatchesPredicate<>(Arrays.asList("john"));
        assertTrue(predicate.test(new PersonBuilder().withName("John SWE").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords - should not occur if NameParser works
        NameMatchesPredicate<Club> predicate = new NameMatchesPredicate<>(Collections.emptyList());
        assertFalse(predicate.test(new ClubBuilder().withName("Jane").build()));

        // Non-matching keyword
        predicate = new NameMatchesPredicate<>(Arrays.asList("john", "janette"));
        assertFalse(predicate.test(new ClubBuilder().withName("Jane").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        NameMatchesPredicate<Person> predicate = new NameMatchesPredicate<>(keywords);

        String expected = NameMatchesPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
