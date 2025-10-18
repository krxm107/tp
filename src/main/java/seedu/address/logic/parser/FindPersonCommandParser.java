package seedu.address.logic.parser;

import java.util.function.Predicate;

import seedu.address.logic.commands.FindPersonCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.search.PersonNameParser;
import seedu.address.logic.parser.search.PersonTagParser;
import seedu.address.model.person.Person;

/**
 * Parses input arguments and creates a new FindPersonCommand object
 */
public class FindPersonCommandParser implements Parser<FindPersonCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindPersonCommand
     * and returns a FindPersonCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindPersonCommand parse(String args) throws ParseException {
        Predicate<Person> predicate = person -> true;
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
            case PersonNameParser.KEYWORD:
                predicate = predicate.and(PersonNameParser.parse(searchParameter));
                break;
            case PersonTagParser.KEYWORD:
                predicate = predicate.and(PersonTagParser.parse(searchParameter));
                break;
            default:
                throw new ParseException("Unknown search keyword");
            }
        }

        return new FindPersonCommand(predicate);
    }

}
