package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.MembershipPersonCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.search.predicates.MembershipStatusPredicate;
import seedu.address.model.membership.MembershipStatus;

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
        String[] parts = args.trim().split(" ", 2);
        Index index;

        try {
            index = ParserUtil.parseIndex(parts[0]);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MembershipPersonCommand.MESSAGE_USAGE), pe);
        }

        if (parts.length == 1) {
            return new MembershipPersonCommand(index,
                    new MembershipStatusPredicate(MembershipStatus.getDefaultStatuses()));
        } else if (!MembershipStatus.containsStatus(parts[1])) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MembershipPersonCommand.MESSAGE_USAGE));
        } else {
            return new MembershipPersonCommand(index,
                    new MembershipStatusPredicate(MembershipStatus.getStatuses(parts[1])));
        }
    }

}
