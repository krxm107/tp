package seedu.address.logic.search.parsers;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.search.SearchParserTestUtil.assertParseFailure;
import static seedu.address.logic.search.SearchParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.Collections;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.address.logic.search.predicates.EmailMatchesPredicate;
import seedu.address.model.club.Club;

public class EmailParserTest {

    private EmailParser<Club> parser = new EmailParser<>();

    @Test
    public void parse_validArgs_returnsCorrectPredicate() {
        // one keyword
        Predicate<Club> expectedPredicate =
                new EmailMatchesPredicate<>(Collections.singletonList("archery"));

        assertParseSuccess(parser, " archery ", expectedPredicate);

        // multiple keywords
        expectedPredicate = new EmailMatchesPredicate<>(Arrays.asList("archery", "gmail"));
        assertParseSuccess(parser, " \n archery \n \t gmail  \t", expectedPredicate);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // empty argument
        assertParseFailure(parser, "   ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchParser.MESSAGE_USAGE));
    }

}
