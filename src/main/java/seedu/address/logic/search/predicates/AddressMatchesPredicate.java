package seedu.address.logic.search.predicates;

import static seedu.address.logic.search.SearchUtil.containsSubstring;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.field.Searchable;

/**
 * Tests that a {@code Searchable}'s {@code Address} matches any of the keywords given.
 */
public class AddressMatchesPredicate<T extends Searchable> implements Predicate<T> {
    private final List<String> keywords;

    public AddressMatchesPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Searchable searchable) {
        return keywords.stream()
                .anyMatch(keyword -> containsSubstring(keyword, searchable.getAddress().value));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddressMatchesPredicate<?>)) {
            return false;
        }

        AddressMatchesPredicate<?> otherAddressMatchesPredicate = (AddressMatchesPredicate<?>) other;
        return keywords.equals(otherAddressMatchesPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
