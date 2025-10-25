package seedu.address.logic.search;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * Stores a list of <code>Predicate</code> objects of the parameterized type
 * and chains them using the and operator when testing
 * @param <T> parameterized type of Predicate objects accepted
 */
public class CombinedSearchPredicate<T> implements Predicate<T> {
    private final List<Predicate<T>> predicates = new ArrayList<>();

    /**
     * Adds a <code>Predicate</code> object of the specified type to the list
     */
    public void add(Predicate<T> predicate) {
        this.predicates.add(predicate);
    }

    @Override
    public boolean test(T obj) {
        Optional<Predicate<T>> predicate = predicates.stream().reduce(Predicate::and);
        return predicate.map(p -> p.test(obj)).orElse(true);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CombinedSearchPredicate<?>)) {
            return false;
        }

        CombinedSearchPredicate<?> otherCombinedSearchPredicate = (CombinedSearchPredicate<?>) other;
        return predicates.equals(otherCombinedSearchPredicate.predicates);
    }

}
