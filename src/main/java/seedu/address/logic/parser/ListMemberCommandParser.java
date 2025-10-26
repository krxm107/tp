package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ListMemberCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ListMemberCommand object
 */
public class ListMemberCommandParser implements Parser<ListMemberCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ListMemberCommand
     * and returns a ListMemberCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListMemberCommand parse(String args) throws ParseException {
        String[] parts = args.split("/");
        Index index;

        try {
            index = ParserUtil.parseIndex(parts[0]);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListMemberCommand.MESSAGE_USAGE), pe);
        }

        if (parts.length == 1) {
            return new ListMemberCommand(index, new MembershipStatusParser().parse(""));
        } else {
            return new ListMemberCommand(index, new MembershipStatusParser().parse(parts[1]));
        }
    }

}
