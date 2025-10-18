package seedu.address.logic.parser.search;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.function.Predicate;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.club.Club;
import seedu.address.model.tag.TagContainsKeywordsPredicate;

/**
 * Parses input arguments for a search by club tag instruction
 */
public class ClubTagParser {

    public static final String KEYWORD = "t";

    /**
     * Parses the given {@code String} of arguments in the context of a search by club tag
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
        Predicate<Club> parseClubTag = club -> club.getTags().stream()
                .anyMatch(new TagContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
        return parseClubTag;
    }

}
