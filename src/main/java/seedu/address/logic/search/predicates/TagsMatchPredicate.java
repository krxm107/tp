package seedu.address.logic.search.predicates;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.field.Searchable;

/**
 * Tests that a {@code Searchable}'s {@code Tag}s match any of the keywords given.
 */
public class TagsMatchPredicate<T extends Searchable> implements Predicate<T> {
    private final List<String> keywords;

    public TagsMatchPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Searchable searchable) {
        return searchable.getTags().stream().anyMatch(new TagContainsKeywordsPredicate(keywords));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TagsMatchPredicate<?>)) {
            return false;
        }

        TagsMatchPredicate<?> otherTagsMatchPredicate = (TagsMatchPredicate<?>) other;
        return keywords.equals(otherTagsMatchPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
