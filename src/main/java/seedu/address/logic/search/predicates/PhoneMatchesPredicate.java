package seedu.address.logic.search.predicates;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.field.Searchable;

import java.util.List;
import java.util.function.Predicate;

public class PhoneMatchesPredicate<T extends Searchable> implements Predicate<T> {
    private final List<String> keywords;

    public PhoneMatchesPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Searchable searchable) {
        return keywords.stream()
                .anyMatch(keyword -> searchable.getPhone().value.contains(keyword));
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
