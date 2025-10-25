package seedu.address.logic.search.predicates;

import static seedu.address.logic.search.SearchUtil.containsSubstring;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.field.Searchable;

/**
 * Tests that a {@code Searchable}'s {@code Phone} matches any of the keywords given.
 */
public class PhoneMatchesPredicate<T extends Searchable> implements Predicate<T> {
    private final List<String> keywords;

    public PhoneMatchesPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Searchable searchable) {
        return keywords.stream()
                .anyMatch(keyword -> containsSubstring(keyword, searchable.getPhone().value));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PhoneMatchesPredicate<?>)) {
            return false;
        }

        PhoneMatchesPredicate<?> otherPhoneMatchesPredicate = (PhoneMatchesPredicate<?>) other;
        return keywords.equals(otherPhoneMatchesPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
