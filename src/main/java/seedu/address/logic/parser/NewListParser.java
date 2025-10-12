package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.function.Predicate;
import java.util.stream.Stream;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.ListMemberCommand;
import seedu.address.logic.commands.ListPersonCommand;
import seedu.address.logic.commands.NewListCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Person;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class NewListParser implements Parser<NewListCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public NewListCommand parse(String args) throws ParseException {
        // base case: essentially same as the current list
        Predicate<Person> predicate = x -> true;

        String[] searchModifiers = args.trim().split("\\\\");

        for (int i = 0; i < searchModifiers.length; i++) {
            // get next search keyword alongside search parameter
            String[] parts = args.split("\\s+", 2);
            if (parts.length < 2) {
                throw new ParseException("Expected value after keyword");
            }

            String searchKeyword = parts[0].trim();
            String searchParameter = parts[1].trim();

            // only this switch statement needs to be edited when a new seach keyword is added
            switch (searchKeyword) {
                // each "parser" here returns a predicate
                case NameParser.KEYWORD:
                    predicate = predicate.and(NameParser.parse(searchParameter));
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

        return new NewListCommand(predicate);
    }

}
