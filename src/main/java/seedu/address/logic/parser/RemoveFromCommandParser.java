package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLUB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEMBER;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddToCommand;
import seedu.address.logic.commands.RemoveFromCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new RemoveFromCommand object
 */
public class RemoveFromCommandParser implements Parser<RemoveFromCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the RemoveFromCommand
     * and returns a RemoveFromCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemoveFromCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_MEMBER, PREFIX_CLUB);
        if (!arePrefixesPresent(argMultimap, PREFIX_MEMBER, PREFIX_CLUB)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveFromCommand.MESSAGE_USAGE));
        }

        try {
            argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_MEMBER, PREFIX_CLUB);
            Index[] personIndexes = ParserUtil.parseIndexes(argMultimap.getValue(PREFIX_MEMBER).get());
            Index[] clubIndexes = ParserUtil.parseIndexes(argMultimap.getValue(PREFIX_CLUB).get());
            return new RemoveFromCommand(personIndexes, clubIndexes);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveFromCommand.MESSAGE_USAGE), pe);
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
