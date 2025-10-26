package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindPersonCommand;
import seedu.address.logic.search.CombinedSearchPredicate;
import seedu.address.logic.search.parsers.SearchParser;
import seedu.address.logic.search.predicates.NameMatchesPredicate;
import seedu.address.logic.search.predicates.TagsMatchPredicate;
import seedu.address.model.person.Person;

public class FindPersonCommandParserTest {

    private FindPersonCommandParser parser = new FindPersonCommandParser();

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no search modifiers (corresponds to find all)
        CombinedSearchPredicate<Person> predicate = new CombinedSearchPredicate<>();
        FindPersonCommand expectedFindCommand = new FindPersonCommand(predicate);

        assertParseSuccess(parser, "     ", expectedFindCommand);

        // no leading and trailing whitespaces
        predicate.add(new NameMatchesPredicate<>(Arrays.asList("Alice", "Bob")));
        expectedFindCommand = new FindPersonCommand(predicate);

        assertParseSuccess(parser, " n/ Alice Bob", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " n/ \n Alice \n \t Bob  \t", expectedFindCommand);

        // multiple search modifiers of the same type
        predicate.add(new NameMatchesPredicate<>(List.of("Charlie")));
        expectedFindCommand = new FindPersonCommand(predicate);

        assertParseSuccess(parser, " n/ Alice Bob n/ Charlie", expectedFindCommand);

        // multiple search modifiers of different types
        predicate.add(new TagsMatchPredicate<>(List.of("friends")));
        expectedFindCommand = new FindPersonCommand(predicate);

        assertParseSuccess(parser, " n/ Alice Bob n/ Charlie t/ friends", expectedFindCommand);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // invalid arguments
        assertParseFailure(parser, " nn/ nickname",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindPersonCommand.MESSAGE_USAGE));

        // search modifier without search parameter
        assertParseFailure(parser, " n/ t/ friends",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchParser.MESSAGE_USAGE));
    }

}
