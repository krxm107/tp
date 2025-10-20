package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.function.Predicate;

import seedu.address.logic.commands.FindClubCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.search.ClubNameParser;
import seedu.address.logic.parser.search.ClubTagParser;
import seedu.address.model.club.Club;

/**
 * Parses input arguments and creates a new FindClubCommand object
 */
public class FindClubCommandParser implements Parser<FindClubCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindClubCommand
     * and returns a FindClubCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindClubCommand parse(String args) throws ParseException {
        Predicate<Club> predicate = club -> true;

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(
                args, PREFIX_NAME, PREFIX_TAG);

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        for (String prefix : argMultimap.getAllValues(PREFIX_NAME)) {
            predicate = predicate.and(ClubNameParser.parse(prefix));
        }
        for (String prefix : argMultimap.getAllValues(PREFIX_TAG)) {
            predicate = predicate.and(ClubTagParser.parse(prefix));
        }

        return new FindClubCommand(predicate);
    }

}
