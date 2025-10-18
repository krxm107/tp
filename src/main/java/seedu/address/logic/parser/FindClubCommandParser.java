package seedu.address.logic.parser;

import java.util.function.Predicate;

import seedu.address.logic.commands.FindClubCommand;
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
        String[] searchModifiers = args.trim().split("\\\\");

        for (String segment : searchModifiers) {
            if (segment.isEmpty()) {
                continue;
            }

            String[] parts = segment.split("\\s+", 2);
            if (parts.length < 2) {
                throw new ParseException("Expected value after keyword");
            }
            String searchKeyword = parts[0].trim();
            String searchParameter = parts[1].trim();

            switch (searchKeyword) {
            case ClubNameParser.KEYWORD:
                predicate = predicate.and(ClubNameParser.parse(searchParameter));
                break;
            case ClubTagParser.KEYWORD:
                predicate = predicate.and(ClubTagParser.parse(searchParameter));
                break;
            default:
                throw new ParseException("Unknown search keyword");
            }
        }

        return new FindClubCommand(predicate);
    }

}
