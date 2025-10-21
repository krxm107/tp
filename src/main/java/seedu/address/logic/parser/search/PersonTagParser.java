package seedu.address.logic.parser.search;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.function.Predicate;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonContainsTagsPredicate;

/**
 * Parses input arguments for a search by person name instruction
 */
public class PersonTagParser implements SearchParser<Person> {

    public static final String KEYWORD = "t";

    /**
     * Parses the given {@code String} of arguments in the context of a search by person tag
     * instruction and returns a corresponding {@code Predicate<Person>} object.
     * @throws ParseException if the user input does not conform the expected format
     */
    public Predicate<Person> parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        }

        String[] tagKeywords = trimmedArgs.split("\\s+");
        return new PersonContainsTagsPredicate(Arrays.asList(tagKeywords));
    }

}
