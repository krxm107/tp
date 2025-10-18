package seedu.address.logic.parser.search;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.function.Predicate;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.club.Club;
import seedu.address.model.club.ClubContainsKeywordsPredicate;

/**
 * Parses input arguments for a search by club name instruction
 */
public class ClubNameParser {

    public static final String KEYWORD = "n";

    /**
     * Parses the given {@code String} of arguments in the context of a search by club name
     * instruction and returns a corresponding {@code Predicate<Club>} object.
     * @throws ParseException if the user input does not conform the expected format
     */
    public static Predicate<Club> parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");
        return new ClubContainsKeywordsPredicate(Arrays.asList(nameKeywords));
    }

}
