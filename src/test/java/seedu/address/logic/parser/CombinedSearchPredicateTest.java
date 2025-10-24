package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.club.Club;
import seedu.address.model.club.ClubContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonContainsTagsPredicate;
import seedu.address.testutil.PersonBuilder;

public class CombinedSearchPredicateTest {

    @Test
    public void equals() {
        CombinedSearchPredicate<Person> firstPredicate = new CombinedSearchPredicate<>();
        CombinedSearchPredicate<Person> secondPredicate = new CombinedSearchPredicate<>();
        CombinedSearchPredicate<Club> thirdPredicate = new CombinedSearchPredicate<>();

        firstPredicate.add(new NameContainsKeywordsPredicate(Collections.singletonList("first")));
        secondPredicate.add(new NameContainsKeywordsPredicate(Collections.singletonList("second")));
        thirdPredicate.add(new ClubContainsKeywordsPredicate(Collections.singletonList("first")));

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        CombinedSearchPredicate<Person> firstPredicateCopy = new CombinedSearchPredicate<>();
        firstPredicateCopy.add(new NameContainsKeywordsPredicate(Collections.singletonList("first")));
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(thirdPredicate));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different values -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));

        // one empty predicate -> returns false
        CombinedSearchPredicate<Person> fourthPredicate = new CombinedSearchPredicate<>();
        assertFalse(firstPredicate.equals(fourthPredicate));

        // both empty predicate -> returns true
        CombinedSearchPredicate<Person> fifthPredicate = new CombinedSearchPredicate<>();
        assertTrue(fifthPredicate.equals(fourthPredicate));
    }

    @Test
    public void test_noPredicate_returnsTrue() {
        CombinedSearchPredicate<Person> predicate = new CombinedSearchPredicate<>();
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").withTags("family").build()));
    }

    @Test
    public void test_allPredicatesMet_returnsTrue() {
        CombinedSearchPredicate<Person> predicate = new CombinedSearchPredicate<>();
        predicate.add(new NameContainsKeywordsPredicate(Collections.singletonList("Alice")));
        predicate.add(new NameContainsKeywordsPredicate(Collections.singletonList("Bob")));
        predicate.add(new PersonContainsTagsPredicate(Collections.singletonList("family")));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").withTags("family").build()));
    }

    @Test
    public void test_onePredicateUnmet_returnsFalse() {
        CombinedSearchPredicate<Person> predicate = new CombinedSearchPredicate<>();
        predicate.add(new NameContainsKeywordsPredicate(Collections.singletonList("Alice")));
        predicate.add(new NameContainsKeywordsPredicate(Collections.singletonList("Bob")));
        predicate.add(new PersonContainsTagsPredicate(Collections.singletonList("family")));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").withTags("friends").build()));
    }

}
