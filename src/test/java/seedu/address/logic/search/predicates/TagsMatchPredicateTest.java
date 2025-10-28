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

public class TagsMatchPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        TagsMatchPredicate<Person> firstPredicate = new TagsMatchPredicate<>(firstPredicateKeywordList);
        TagsMatchPredicate<Person> secondPredicate = new TagsMatchPredicate<>(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        TagsMatchPredicate<Person> firstPredicateCopy = new TagsMatchPredicate<>(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        AddressMatchesPredicate<Person> addressPredicate = new AddressMatchesPredicate<>(firstPredicateKeywordList);
        assertFalse(firstPredicate.equals(addressPredicate));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different tag -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_tagsContainKeywords_returnsTrue() {
        TagsMatchPredicate<Club> predicate = new TagsMatchPredicate<>(Collections.singletonList("Morning"));
        assertTrue(predicate.test(new ClubBuilder().withTags("morning").build()));

        // Substring keywords
        predicate = new TagsMatchPredicate<>(Arrays.asList("morning"));
        assertTrue(predicate.test(new ClubBuilder().withTags("8morning").build()));
    }

    @Test
    public void test_tagsDoNotContainKeywords_returnsFalse() {
        // Zero keywords - should not occur if TagParser works
        TagsMatchPredicate<Person> predicate = new TagsMatchPredicate<>(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withTags("morning").build()));

        // Non-matching keyword
        predicate = new TagsMatchPredicate<>(Arrays.asList("evening", "mornings"));
        assertFalse(predicate.test(new PersonBuilder().withTags("morning").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        TagsMatchPredicate<Person> predicate = new TagsMatchPredicate<>(keywords);

        String expected = TagsMatchPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
