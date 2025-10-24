package seedu.address.logic.parser.search;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.function.Predicate;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.club.Club;
import seedu.address.model.club.ClubContainsTagsPredicate;

/**
 * Parses input arguments for a search by club tag instruction
 */
public class ClubTagParser implements SearchParser<Club> {

    public static final String KEYWORD = "t";

    /**
     * Parses the given {@code String} of arguments in the context of a search by club tag
     * instruction and returns a corresponding {@code Predicate<Club>} object.
     * @throws ParseException if the user input does not conform the expected format
     */
    public Predicate<Club> parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        }

        String[] tagKeywords = trimmedArgs.split("\\s+");
        return new ClubContainsTagsPredicate(Arrays.asList(tagKeywords));
    }

}
