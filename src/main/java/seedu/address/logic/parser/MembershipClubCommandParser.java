package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.MembershipClubCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.search.parsers.StatusParser;
import seedu.address.logic.search.predicates.MembershipStatusPredicate;
import seedu.address.model.membership.MembershipStatus;
import seedu.address.model.person.Person;

/**
 * Parses input arguments and creates a new MembershipClubCommand object
 */
public class MembershipClubCommandParser implements Parser<MembershipClubCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the MembershipClubCommand
     * and returns a MembershipClubCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public MembershipClubCommand parse(String args) throws ParseException {
        String[] parts = args.trim().split(" ", 2);
        Index index;

        try {
            index = ParserUtil.parseIndex(parts[0]);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MembershipClubCommand.MESSAGE_USAGE), pe);
        }

        if (parts.length == 1) {
            return new MembershipClubCommand(index, 
                    new MembershipStatusPredicate(MembershipStatus.getStatuses(parts[1])));
        } else {
            return new MembershipClubCommand(index,
                    new MembershipStatusPredicate(MembershipStatus.getDefaultStatuses()));
        }
    }

}
