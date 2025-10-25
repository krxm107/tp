package seedu.address.logic.search;

import seedu.address.logic.commands.Command;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.search.parsers.SearchParser;
import seedu.address.model.field.Searchable;

import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SearchParserTestUtil {

    /**
     * Asserts that the parsing of {@code userInput} by {@code parser} is successful and the command created
     * equals to {@code expectedCommand}.
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
     * equals to {@code expectedMessage}.
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
