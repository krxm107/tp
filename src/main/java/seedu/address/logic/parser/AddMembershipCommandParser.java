package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLUB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DURATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEMBER;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddMembershipCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AddMembershipCommand object
 */
public class AddMembershipCommandParser implements Parser<AddMembershipCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddMembershipCommand
     * and returns an AddMembershipCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddMembershipCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_MEMBER, PREFIX_CLUB, PREFIX_DURATION);
        if (!arePrefixesPresent(argMultimap, PREFIX_MEMBER, PREFIX_CLUB)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMembershipCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_MEMBER, PREFIX_CLUB, PREFIX_DURATION);

        try {
            Index[] personIndexes = ParserUtil.parseIndexes(argMultimap.getValue(PREFIX_MEMBER).get());
            Index[] clubIndexes = ParserUtil.parseIndexes(argMultimap.getValue(PREFIX_CLUB).get());
            // duration is optional
            if (argMultimap.getValue(PREFIX_DURATION).isEmpty()) {
                return new AddMembershipCommand(personIndexes, clubIndexes);
            }
            int durationInMonths = Integer.parseInt(argMultimap.getValue(PREFIX_DURATION).get());
            return new AddMembershipCommand(personIndexes, clubIndexes, durationInMonths);
        } catch (NumberFormatException | ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMembershipCommand.MESSAGE_USAGE), pe);
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
