package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLUB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import seedu.address.logic.commands.RemoveFromCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.field.Name;

/**
 * Parses input arguments and creates a new AddToCommand object
 */
public class RemoveFromCommandParser implements Parser<RemoveFromCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddToCommand
     * and returns an AddToCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemoveFromCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_CLUB, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL);

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_CLUB, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL);
        Name personName = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Name clubName = ParserUtil.parseName(argMultimap.getValue(PREFIX_CLUB).get());

        return new RemoveFromCommand(personName, clubName);
    }
}
