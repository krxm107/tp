package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLUB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DURATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEMBER;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RenewMembershipCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new RenewMembershipCommand object
 */
public class RenewMembershipCommandParser implements Parser<RenewMembershipCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RenewMembershipCommand
     * and returns a RenewMembershipCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RenewMembershipCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_MEMBER, PREFIX_CLUB, PREFIX_DURATION);
        if (!arePrefixesPresent(argMultimap, PREFIX_MEMBER, PREFIX_CLUB, PREFIX_DURATION)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            RenewMembershipCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_MEMBER, PREFIX_CLUB, PREFIX_DURATION);

        try {
            Index personIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_MEMBER).get());
            Index clubIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_CLUB).get());
            int durationInMonths = Integer.parseInt(argMultimap.getValue(CliSyntax.PREFIX_DURATION).get());
            return new RenewMembershipCommand(personIndex, clubIndex, durationInMonths);
        } catch (NumberFormatException | ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RenewMembershipCommand.MESSAGE_USAGE), pe);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
