package seedu.address.logic.parser;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteClubCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteClubCommandParser implements Parser<DeleteClubCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteClubCommand
     * and returns a DeleteClubCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteClubCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteClubCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteClubCommand.MESSAGE_USAGE), pe);
        }
    }

}
