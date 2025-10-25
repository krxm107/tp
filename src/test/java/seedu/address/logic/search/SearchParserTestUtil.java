package seedu.address.logic.search;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.function.Predicate;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.search.parsers.SearchParser;
import seedu.address.model.field.Searchable;

/**
 * Contains helper methods for testing search parsers.
 */
public class SearchParserTestUtil {

    /**
     * Asserts that the parsing of {@code userInput} by {@code parser} is successful and the predicate created
     * equals to {@code expectedPredicate}.
     */
    public static <T extends Searchable> void assertParseSuccess(SearchParser<T> parser, String userInput,
                                                                 Predicate<T> expectedPredicate) {
        try {
            Predicate<T> predicate = parser.parse(userInput);
            assertEquals(expectedPredicate, predicate);
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Invalid userInput.", pe);
        }
    }

    /**
     * Asserts that the parsing of {@code userInput} by {@code parser} is unsuccessful and the error message
     * equals to {@code expectedPredicate}.
     */
    public static <T extends Searchable> void assertParseFailure(SearchParser<T> parser,
                                                                 String userInput, String expectedMessage) {
        try {
            parser.parse(userInput);
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(expectedMessage, pe.getMessage());
        }
    }

}
