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

public class EmailMatchesPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        EmailMatchesPredicate<Person> firstPredicate = new EmailMatchesPredicate<>(firstPredicateKeywordList);
        EmailMatchesPredicate<Person> secondPredicate = new EmailMatchesPredicate<>(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        EmailMatchesPredicate<Person> firstPredicateCopy = new EmailMatchesPredicate<>(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        NameMatchesPredicate<Person> namePredicate = new NameMatchesPredicate<>(firstPredicateKeywordList);
        assertFalse(firstPredicate.equals(namePredicate));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different tag -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_emailContainsKeywords_returnsTrue() {
        EmailMatchesPredicate<Person> predicate =
                new EmailMatchesPredicate<>(Collections.singletonList("Archery@gmail.com"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("archery@gmail.com").build()));

        // Substring keywords
        predicate = new EmailMatchesPredicate<>(Collections.singletonList("archery"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("archery@gmail.com").build()));
    }

    @Test
    public void test_emailDoesNotContainKeywords_returnsFalse() {
        // Zero keywords - should not occur if EmailParser works
        EmailMatchesPredicate<Club> predicate = new EmailMatchesPredicate<>(Collections.emptyList());
        assertFalse(predicate.test(new ClubBuilder().withEmail("archery@gmail.com").build()));

        // Non-matching keyword
        predicate = new EmailMatchesPredicate<>(Arrays.asList("basketball@gmail.com", "archerynus@gmail.com"));
        assertFalse(predicate.test(new ClubBuilder().withEmail("archery@gmail.com").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        EmailMatchesPredicate<Person> predicate = new EmailMatchesPredicate<>(keywords);

        String expected = EmailMatchesPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
