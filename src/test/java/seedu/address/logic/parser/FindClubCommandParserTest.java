package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindClubCommand;
import seedu.address.logic.parser.search.SearchParser;
import seedu.address.model.club.Club;
import seedu.address.model.club.ClubContainsKeywordsPredicate;
import seedu.address.model.club.ClubContainsTagsPredicate;

public class FindClubCommandParserTest {

    private FindClubCommandParser parser = new FindClubCommandParser();

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no search modifiers (corresponds to find all)
        FindCommandPredicate<Club> predicate = new FindCommandPredicate<>();
        FindClubCommand expectedFindCommand = new FindClubCommand(predicate);

        assertParseSuccess(parser, "     ", expectedFindCommand);

        // no leading and trailing whitespaces
        predicate.add(new ClubContainsKeywordsPredicate(Arrays.asList("Archery", "Bowling")));
        expectedFindCommand = new FindClubCommand(predicate);

        assertParseSuccess(parser, " n/ Archery Bowling", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " n/ \n Archery \n \t Bowling  \t", expectedFindCommand);

        // multiple search modifiers of the same type
        predicate.add(new ClubContainsKeywordsPredicate(List.of("Canoeing")));
        expectedFindCommand = new FindClubCommand(predicate);

        assertParseSuccess(parser, " n/ Archery Bowling n/ Canoeing", expectedFindCommand);

        // multiple search modifiers of different types
        predicate.add(new ClubContainsTagsPredicate(List.of("NUS")));
        expectedFindCommand = new FindClubCommand(predicate);

        assertParseSuccess(parser, " n/ Archery Bowling n/ Canoeing t/ NUS", expectedFindCommand);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // invalid arguments
        assertParseFailure(parser, " a/ address",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindClubCommand.MESSAGE_USAGE));

        // search modifier without search parameter
        assertParseFailure(parser, " n/ t/ NUS",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchParser.MESSAGE_USAGE));
    }

}
