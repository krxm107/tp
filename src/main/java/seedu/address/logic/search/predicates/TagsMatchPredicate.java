package seedu.address.logic.search.predicates;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.field.Searchable;
import seedu.address.model.tag.TagContainsKeywordsPredicate;

import java.util.List;
import java.util.function.Predicate;

/**
 * Tests that a {@code Searchable}'s {@code Tag}s match any of the keywords given.
 */
public class TagsMatchPredicate<T extends Searchable> implements Predicate<T> {
    private final List<String> tags;

    public TagsMatchPredicate(List<String> tags) {
        this.tags = tags;
    }

    @Override
    public boolean test(Searchable searchable) {
        return searchable.getTags().stream().anyMatch(new TagContainsKeywordsPredicate(tags));
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
        return tags.equals(otherTagsMatchPredicate.tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("tags", tags).toString();
    }
}
