package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ListMemberCommand;
import seedu.address.logic.commands.ListMembershipCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ListMembershipCommand object
 */
public class ListMembershipCommandParser implements Parser<ListMembershipCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ListMembershipCommand
     * and returns a ListMembershipCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListMembershipCommand parse(String args) throws ParseException {
        String[] parts = args.split("/");
        Index index;

        try {
            index = ParserUtil.parseIndex(args);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListMembershipCommand.MESSAGE_USAGE), pe);
        }

        if (parts.length == 1) {
            return new ListMembershipCommand(index);
        } else {
            return new ListMembershipCommand(index);
        }
    }

}
