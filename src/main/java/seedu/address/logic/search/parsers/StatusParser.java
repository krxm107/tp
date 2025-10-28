package seedu.address.logic.search.parsers;

import java.util.function.Predicate;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.search.predicates.StatusesMatchPredicate;
import seedu.address.model.field.Searchable;
import seedu.address.model.membership.MembershipStatus;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

/**
 * Parses input arguments for a search by tag instruction
 */
public class StatusParser<T extends Searchable> implements SearchParser<T> {

    /**
     * Parses the given {@code String} of arguments in the context of a search by membership
     * status instruction and returns a corresponding {@code Predicate<Searchable>} object.
     * @throws ParseException if the user input does not conform the expected format
     */
    public Predicate<T> parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        }

        return new StatusesMatchPredicate<>(MembershipStatus.getStatuses(trimmedArgs));
    }

}
