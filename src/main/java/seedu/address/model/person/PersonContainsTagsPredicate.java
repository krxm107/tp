package seedu.address.model.person;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.club.Club;
import seedu.address.model.club.ClubContainsKeywordsPredicate;
import seedu.address.model.tag.TagContainsKeywordsPredicate;

import java.util.List;
import java.util.function.Predicate;

/**
 * Tests whether any of a {@code Person}'s {@code Tag}s matches any of the keywords given.
 */
public class PersonContainsTagsPredicate implements Predicate<Person> {
    private final List<String> tags;

    public PersonContainsTagsPredicate(List<String> tags) {
        this.tags = tags;
    }

    @Override
    public boolean test(Person person) {
        return person.getTags().stream().anyMatch(new TagContainsKeywordsPredicate(tags));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonContainsTagsPredicate)) {
            return false;
        }

        PersonContainsTagsPredicate otherPersonContainsTagsPredicate = (PersonContainsTagsPredicate) other;
        return tags.equals(otherPersonContainsTagsPredicate.tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("tags", tags).toString();
    }
}
