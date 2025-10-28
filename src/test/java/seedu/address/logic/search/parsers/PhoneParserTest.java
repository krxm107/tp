package seedu.address.logic.search.parsers;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.search.SearchParserTestUtil.assertParseFailure;
import static seedu.address.logic.search.SearchParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.Collections;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.address.logic.search.predicates.PhoneMatchesPredicate;
import seedu.address.model.club.Club;

public class PhoneParserTest {

    private PhoneParser<Club> parser = new PhoneParser<>();

    @Test
    public void parse_validArgs_returnsCorrectPredicate() {
        // one keyword
        Predicate<Club> expectedPredicate =
                new PhoneMatchesPredicate<>(Collections.singletonList("8888"));

        assertParseSuccess(parser, " 8888 ", expectedPredicate);

        // multiple keywords
        expectedPredicate = new PhoneMatchesPredicate<>(Arrays.asList("5275", "2725"));
        assertParseSuccess(parser, " \n 5275 \n \t 2725  \t", expectedPredicate);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // empty argument
        assertParseFailure(parser, "   ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchParser.MESSAGE_USAGE));
    }

}
