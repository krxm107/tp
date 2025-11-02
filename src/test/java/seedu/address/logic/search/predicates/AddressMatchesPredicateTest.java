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

public class AddressMatchesPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        AddressMatchesPredicate<Person> firstPredicate = new AddressMatchesPredicate<>(firstPredicateKeywordList);
        AddressMatchesPredicate<Person> secondPredicate = new AddressMatchesPredicate<>(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        AddressMatchesPredicate<Person> firstPredicateCopy = new AddressMatchesPredicate<>(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        EmailMatchesPredicate<Person> emailPredicate = new EmailMatchesPredicate<>(firstPredicateKeywordList);
        assertFalse(firstPredicate.equals(emailPredicate));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different tag -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_addressContainsKeywords_returnsTrue() {
        AddressMatchesPredicate<Person> predicate = new AddressMatchesPredicate<>(Collections.singletonList("jurong"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("Jurong").build()));

        // Substring keyword
        predicate = new AddressMatchesPredicate<>(Arrays.asList("jurong"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("Jurong Avenue 5").build()));
    }

    @Test
    public void test_addressDoesNotContainKeywords_returnsFalse() {
        // Zero keywords - should not occur if AddressParser works
        AddressMatchesPredicate<Club> predicate = new AddressMatchesPredicate<>(Collections.emptyList());
        assertFalse(predicate.test(new ClubBuilder().withAddress("NUS").build()));

        // Empty field
        predicate = new AddressMatchesPredicate<>(Collections.singletonList("NUS"));
        assertFalse(predicate.test(new ClubBuilder().withAddress("").build()));

        // Non-matching keyword
        predicate = new AddressMatchesPredicate<>(Arrays.asList("SMU", "NUSS"));
        assertFalse(predicate.test(new ClubBuilder().withAddress("NUS").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        AddressMatchesPredicate<Person> predicate = new AddressMatchesPredicate<>(keywords);

        String expected = AddressMatchesPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
