package seedu.address.logic.parser;

import java.util.function.Predicate;

import seedu.address.logic.commands.NewListPersonCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Person;

/**
 * Parses input arguments and creates a new NewListPersonCommand object
 */
public class NewListPersonParser implements Parser<NewListPersonCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the NewListPersonCommand
     * and returns a NewListPersonCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public NewListPersonCommand parse(String args) throws ParseException {
        // base case: essentially same as the current list
        Predicate<Person> predicate = x -> true;

        String[] searchModifiers = args.trim().split("\\\\");

        for (String segment : searchModifiers) {
            if (segment.isEmpty()) {
                continue;
            }

            // get next search keyword alongside search parameter
            String[] parts = segment.split("\\s+", 2);
            if (parts.length < 2) {
                throw new ParseException("Expected value after keyword");
            }

            String searchKeyword = parts[0].trim();
            String searchParameter = parts[1].trim();

            // only this switch statement needs to be edited when a new seach keyword is added
            switch (searchKeyword) {
                // each "parser" here returns a predicate
                case PersonNameParser.KEYWORD:
                    predicate = predicate.and(PersonNameParser.parse(searchParameter));
                    break;
                    /*
                case TagParser.KEYWORD:
                    predicate = predicate.and(TagParser.parse(value));
                    break;
                     */
                default:
                    throw new ParseException("Unknown search keyword");
            }
        }

        return new NewListPersonCommand(predicate);
    }

}
