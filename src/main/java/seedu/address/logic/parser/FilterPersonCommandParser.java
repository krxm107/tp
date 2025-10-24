package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.FilterPersonCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.FilterPersonPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FilterPersonCommandParser implements Parser<FilterPersonCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FilterPersonCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterPersonCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split(" (?=\\w/)");

        return new FilterPersonCommand(new FilterPersonPredicate(Arrays.asList(nameKeywords)));
    }
}
