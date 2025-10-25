package seedu.address.logic.search.parsers;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.search.SearchParserTestUtil.assertParseFailure;
import static seedu.address.logic.search.SearchParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.Collections;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.address.logic.search.predicates.NameMatchesPredicate;
import seedu.address.model.person.Person;

public class NameParserTest {

    private NameParser<Person> parser = new NameParser<>();

    @Test
    public void parse_validArgs_returnsCorrectPredicate() {
        // one keyword
        Predicate<Person> expectedPredicate =
                new NameMatchesPredicate<>(Collections.singletonList("John"));

        assertParseSuccess(parser, " John ", expectedPredicate);

        // multiple keywords
        expectedPredicate = new NameMatchesPredicate<>(Arrays.asList("John", "Jane"));
        assertParseSuccess(parser, " \n John \n \t Jane  \t", expectedPredicate);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // empty argument
        assertParseFailure(parser, "   ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchParser.MESSAGE_USAGE));
    }

}
