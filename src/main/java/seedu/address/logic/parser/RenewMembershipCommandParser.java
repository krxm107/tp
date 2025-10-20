package seedu.address.logic.parser;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RenewMembershipCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import java.util.stream.Stream;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLUB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DURATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEMBER;

public class RenewMembershipCommandParser {

    public RenewMembershipCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_MEMBER, PREFIX_CLUB, PREFIX_DURATION);
        if (!arePrefixesPresent(argMultimap, PREFIX_MEMBER, PREFIX_CLUB)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RenewMembershipCommand.MESSAGE_USAGE));
        }
        try {
            argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_MEMBER, PREFIX_CLUB);
            Index personIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_MEMBER).get());
            Index clubIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_CLUB).get());
            int durationInMonths = Integer.parseInt(argMultimap.getValue(CliSyntax.PREFIX_DURATION).get());
            return new RenewMembershipCommand(personIndex, clubIndex, durationInMonths);
        } catch (ParseException pe) {
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
