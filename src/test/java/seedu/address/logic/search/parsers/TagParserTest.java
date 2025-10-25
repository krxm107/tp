package seedu.address.logic.search.parsers;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.search.SearchParserTestUtil.assertParseFailure;
import static seedu.address.logic.search.SearchParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.Collections;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.address.logic.search.predicates.TagsMatchPredicate;
import seedu.address.model.person.Person;


public class TagParserTest {

    private TagParser<Person> parser = new TagParser<>();

    @Test
    public void parse_validArgs_returnsCorrectPredicate() {
        // one keyword
        Predicate<Person> expectedPredicate =
                new TagsMatchPredicate<>(Collections.singletonList("friend"));

        assertParseSuccess(parser, " friend ", expectedPredicate);

        // multiple keywords
        expectedPredicate = new TagsMatchPredicate<>(Arrays.asList("friend", "nus"));
        assertParseSuccess(parser, " \n friend \n \t nus  \t", expectedPredicate);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // empty argument
        assertParseFailure(parser, "   ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchParser.MESSAGE_USAGE));
    }

}
