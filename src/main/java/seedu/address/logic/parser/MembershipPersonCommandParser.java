package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.MembershipPersonCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new MembershipPersonCommand object
 */
public class MembershipPersonCommandParser implements Parser<MembershipPersonCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the MembershipPersonCommand
     * and returns a MembershipPersonCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public MembershipPersonCommand parse(String args) throws ParseException {
        String[] parts = args.split("/");
        Index index;

        try {
            index = ParserUtil.parseIndex(parts[0]);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MembershipPersonCommand.MESSAGE_USAGE), pe);
        }

        if (parts.length == 1) {
            return new MembershipPersonCommand(index, new MembershipStatusParser().parse(""));
        } else {
            return new MembershipPersonCommand(index, new MembershipStatusParser().parse(parts[1]));
        }
    }

}
