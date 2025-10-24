package seedu.address.logic.search.predicates;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.field.Searchable;

import java.util.List;
import java.util.function.Predicate;

public class EmailMatchesPredicate<T extends Searchable> implements Predicate<T> {
    private final List<String> keywords;

    public EmailMatchesPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Searchable searchable) {
        return keywords.stream()
                .anyMatch(keyword -> searchable.getEmail().value.contains(keyword));
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

        EmailMatchesPredicate<?> otherEmailMatchesPredicate = (EmailMatchesPredicate<?>) other;
        return keywords.equals(otherEmailMatchesPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
