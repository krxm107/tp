package seedu.address.logic.search.parsers;

import java.util.function.Predicate;

import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses a specific search instruction in a find command
 * @param <T>
 */
public interface SearchParser<T> {

    String MESSAGE_USAGE = "Parameters: [SEARCH_CONDITIONS SEARCH_KEYWORDS]...\n"
            + "For each condition, one or more search keywords must be provided,"
            + " any of which may be used to match the target.\n";

    /**
     * Parses {@code userInput} into a predicate and returns it.
     * @throws ParseException if {@code userInput} does not conform the expected format
     */
    Predicate<T> parse(String userInput) throws ParseException;
}
