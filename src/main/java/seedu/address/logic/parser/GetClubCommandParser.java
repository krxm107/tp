package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.GetClubCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new GetClubCommand object
 */
public class GetClubCommandParser implements Parser<GetClubCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the GetClubCommand
     * and returns a GetClubCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public GetClubCommand parse(String args) throws ParseException {
        String[] parts = args.split("/");
        Index index;

        try {
            index = ParserUtil.parseIndex(parts[0]);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, GetClubCommand.MESSAGE_USAGE), pe);
        }

        if (parts.length == 1) {
            return new GetClubCommand(index, "");
        } else {
            return new GetClubCommand(index, parts[1]);
        }
    }

}
