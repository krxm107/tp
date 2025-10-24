package seedu.address.logic.parser.search;

import java.util.function.Predicate;

import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses a specific search instruction in a find command
 * @param <T>
 */
public interface SearchParser<T> {

    String MESSAGE_USAGE = "For each instruction, one or more search parameters must be provided,"
            + " any of which may be used to match the target."
            + "Parameters: /SEARCH_KEYWORD1 [SEARCH_PARAMETERS1] /SEARCH_KEYWORD2 [SEARCH_PARAMETERS2]...\n";

    /**
     * Parses {@code userInput} into a predicate and returns it.
     * @throws ParseException if {@code userInput} does not conform the expected format
     */
    Predicate<T> parse(String userInput) throws ParseException;
}
