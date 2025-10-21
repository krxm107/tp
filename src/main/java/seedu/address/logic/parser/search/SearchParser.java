package seedu.address.logic.parser.search;

import java.util.function.Predicate;

import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses a specific search instruction in a find command
 * @param <T>
 */
public interface SearchParser<T> {

    /**
     * Parses {@code userInput} into a predicate and returns it.
     * @throws ParseException if {@code userInput} does not conform the expected format
     */
    Predicate<T> parse(String userInput) throws ParseException;
}
