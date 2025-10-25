package seedu.address.logic.search.parsers;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.function.Predicate;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.search.predicates.TagsMatchPredicate;
import seedu.address.model.field.Searchable;

/**
 * Parses input arguments for a search by tag instruction
 */
public class TagParser<T extends Searchable> implements SearchParser<T> {

    /**
     * Parses the given {@code String} of arguments in the context of a search by tag
     * instruction and returns a corresponding {@code Predicate<Searchable>} object.
     * @throws ParseException if the user input does not conform the expected format
     */
    public Predicate<T> parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        }

        String[] tagKeywords = trimmedArgs.split("\\s+");
        return new TagsMatchPredicate<>(Arrays.asList(tagKeywords));
    }

}
