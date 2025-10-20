package seedu.address.logic.parser.search;

import seedu.address.logic.parser.exceptions.ParseException;

import java.util.function.Predicate;

public interface SearchParser<T> {

    /**
     * Parses {@code userInput} into a predicate and returns it.
     * @throws ParseException if {@code userInput} does not conform the expected format
     */
    Predicate<T> parse(String userInput) throws ParseException;
}
