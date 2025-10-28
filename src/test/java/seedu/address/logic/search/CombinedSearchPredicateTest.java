package seedu.address.logic.search;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.logic.search.predicates.AddressMatchesPredicate;
import seedu.address.logic.search.predicates.NameMatchesPredicate;
import seedu.address.logic.search.predicates.TagsMatchPredicate;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class CombinedSearchPredicateTest {

    @Test
    public void equals() {
        CombinedSearchPredicate<Person> firstCombinedPredicate = new CombinedSearchPredicate<>();
        CombinedSearchPredicate<Person> secondCombinedPredicate = new CombinedSearchPredicate<>();

        NameMatchesPredicate<Person> firstPredicate = new NameMatchesPredicate<>(Arrays.asList("John", "Jane"));
        NameMatchesPredicate<Person> secondPredicate = new NameMatchesPredicate<>(Arrays.asList("John"));
        AddressMatchesPredicate<Person> thirdPredicate = new AddressMatchesPredicate<>(Arrays.asList("West", "East"));

        // null -> returns false
        assertFalse(firstCombinedPredicate.equals(null));

        // both empty -> returns true
        assertTrue(firstCombinedPredicate.equals(secondCombinedPredicate));

        // only one empty -> returns false
        firstCombinedPredicate.add(firstPredicate);
        assertFalse(firstCombinedPredicate.equals(secondCombinedPredicate));

        // same object -> returns true
        assertTrue(firstCombinedPredicate.equals(firstCombinedPredicate));

        // same predicates -> returns true
        secondCombinedPredicate.add(firstPredicate);
        assertTrue(firstCombinedPredicate.equals(secondCombinedPredicate));

        // multiple same predicates -> returns true
        firstCombinedPredicate.add(thirdPredicate);
        secondCombinedPredicate.add(thirdPredicate);
        assertTrue(firstCombinedPredicate.equals(secondCombinedPredicate));

        // different predicates -> returns false
        firstCombinedPredicate.add(firstPredicate);
        secondCombinedPredicate.add(secondPredicate);
        assertFalse(firstCombinedPredicate.equals(secondCombinedPredicate));

        // different class -> returns false
        assertFalse(firstCombinedPredicate.equals(3));
    }

    @Test
    public void test_personSatisfiesAllPredicates_returnsTrue() {
        CombinedSearchPredicate<Person> predicate = new CombinedSearchPredicate<>();
        predicate.add(new NameMatchesPredicate<>(Collections.singletonList("John")));
        predicate.add(new AddressMatchesPredicate<>(Collections.singletonList("Bedok")));
        predicate.add(new TagsMatchPredicate<>(Collections.singletonList("friend")));
        Person john = new PersonBuilder().withName("John").withAddress("Bedok").withTags("friend").build();
        assertTrue(predicate.test(john));
    }

    @Test
    public void test_emptyPredicate_returnsTrue() {
        CombinedSearchPredicate<Person> predicate = new CombinedSearchPredicate<>();
        assertTrue(predicate.test(new PersonBuilder().build()));
    }

    @Test
    public void test_personDoesNotSatisfyAllPredicates_returnsFalse() {
        CombinedSearchPredicate<Person> predicate = new CombinedSearchPredicate<>();
        predicate.add(new NameMatchesPredicate<>(Collections.singletonList("John")));
        predicate.add(new AddressMatchesPredicate<>(Collections.singletonList("Bedok")));
        predicate.add(new TagsMatchPredicate<>(Collections.singletonList("friend")));
        Person john = new PersonBuilder().withName("John").withAddress("Bedok").withTags("boss").build();
        assertFalse(predicate.test(john));
    }

}
