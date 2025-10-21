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

public class FindCommandPredicateTest {

    @Test
    public void equals() {
        FindCommandPredicate<Person> firstPredicate = new FindCommandPredicate<>();
        FindCommandPredicate<Person> secondPredicate = new FindCommandPredicate<>();
        FindCommandPredicate<Club> thirdPredicate = new FindCommandPredicate<>();

        firstPredicate.add(new NameContainsKeywordsPredicate(Collections.singletonList("first")));
        secondPredicate.add(new NameContainsKeywordsPredicate(Collections.singletonList("second")));
        thirdPredicate.add(new ClubContainsKeywordsPredicate(Collections.singletonList("first")));

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        FindCommandPredicate<Person> firstPredicateCopy = new FindCommandPredicate<>();
        firstPredicateCopy.add(new NameContainsKeywordsPredicate(Collections.singletonList("first")));
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(thirdPredicate));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different values -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));

        // one empty predicate -> returns false
        FindCommandPredicate<Person> fourthPredicate = new FindCommandPredicate<>();
        assertFalse(firstPredicate.equals(fourthPredicate));

        // both empty predicate -> returns true
        FindCommandPredicate<Person> fifthPredicate = new FindCommandPredicate<>();
        assertTrue(fifthPredicate.equals(fourthPredicate));
    }

    @Test
    public void test_noPredicate_returnsTrue() {
        FindCommandPredicate<Person> predicate = new FindCommandPredicate<>();
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").withTags("family").build()));
    }

    @Test
    public void test_allPredicatesMet_returnsTrue() {
        FindCommandPredicate<Person> predicate = new FindCommandPredicate<>();
        predicate.add(new NameContainsKeywordsPredicate(Collections.singletonList("Alice")));
        predicate.add(new NameContainsKeywordsPredicate(Collections.singletonList("Bob")));
        predicate.add(new PersonContainsTagsPredicate(Collections.singletonList("family")));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").withTags("family").build()));
    }

    @Test
    public void test_onePredicateUnmet_returnsFalse() {
        FindCommandPredicate<Person> predicate = new FindCommandPredicate<>();
        predicate.add(new NameContainsKeywordsPredicate(Collections.singletonList("Alice")));
        predicate.add(new NameContainsKeywordsPredicate(Collections.singletonList("Bob")));
        predicate.add(new PersonContainsTagsPredicate(Collections.singletonList("family")));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").withTags("friends").build()));
    }

}
