package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.GetPersonCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new GetPersonCommand object
 */
public class GetPersonCommandParser implements Parser<GetPersonCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the GetPersonCommand
     * and returns a GetPersonCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public GetPersonCommand parse(String args) throws ParseException {
        String[] parts = args.split("/");
        Index index;

        try {
            index = ParserUtil.parseIndex(parts[0]);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, GetPersonCommand.MESSAGE_USAGE), pe);
        }

        if (parts.length == 1) {
            return new GetPersonCommand(index, "");
        } else {
            return new GetPersonCommand(index, parts[1]);
        }
    }

}
