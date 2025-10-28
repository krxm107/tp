package seedu.address.logic.search.parsers;

import java.util.function.Predicate;

import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses a specific search instruction in a find command
 * @param <T>
 */
public interface SearchParser<T> {

    String MESSAGE_USAGE = "Parameters: /SEARCH_INSTRUCTION1 [SEARCH_PARAMETERS1] "
            + "/SEARCH_INSTRUCTION2 [SEARCH_PARAMETERS2]...\n"
            + "For each instruction, one or more search parameters must be provided,"
            + " any of which may be used to match the target.\n";

    /**
     * Parses {@code userInput} into a predicate and returns it.
     * @throws ParseException if {@code userInput} does not conform the expected format
     */
    Predicate<T> parse(String userInput) throws ParseException;
}
