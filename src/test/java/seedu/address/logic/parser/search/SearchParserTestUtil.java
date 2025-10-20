package seedu.address.logic.parser.search;

import static org.junit.jupiter.api.Assertions.assertEquals;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.club.Club;
import seedu.address.model.person.Person;

import java.util.List;
import java.util.function.Predicate;

/**
 * Contains helper methods for testing search parsers (for find commands).
 */
public class SearchParserTestUtil {

    /**
     * Asserts that the parsing of {@code userInput} by {@code parser} is successful and the predicate created
     * equals to {@code expectedCommand}.
     */
    public static void testParseClub(SearchParser<Club> parser, String userInput,
                                     List<Club> testList, List<Club> expectedResult) {
        try {
            Predicate<Club> predicate = parser.parse(userInput);
            assertEquals(testList.stream().filter(predicate).toList(), expectedResult);
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Invalid userInput.", pe);
        }
    }

    /**
     * Asserts that the parsing of {@code userInput} by {@code parser} is unsuccessful and the error message
     * equals to {@code expectedMessage}.
     */
    public static void testParsePerson(SearchParser<Person> parser, String userInput,
                                       List<Person> testList, List<Person> expectedResult) {
        try {
            Predicate<Person> predicate = parser.parse(userInput);
            assertEquals(testList.stream().filter(predicate).toList(), expectedResult);
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Invalid userInput.", pe);
        }
    }

}
