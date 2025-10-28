package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.GetHistoryCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new GetHistoryCommand object
 */
public class GetHistoryCommandParser implements Parser<GetHistoryCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the GetHistoryCommand
     * and returns a GetHistoryCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public GetHistoryCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new GetHistoryCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, GetHistoryCommand.MESSAGE_USAGE), pe);
        }
    }
}
