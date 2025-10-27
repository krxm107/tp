package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Function;

import seedu.address.logic.commands.SortPersonCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Person;

/**
 * Parses input arguments and creates a new SortPersonCommand object.
 */
public class SortPersonCommandParser implements Parser<SortPersonCommand> {

    @Override
    public SortPersonCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {

            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortPersonCommand.MESSAGE_USAGE));
        }

        String[] personFields = trimmedArgs.split("\\s+");

        return new SortPersonCommand(getPersonComparator(personFields));
    }

    private Comparator<Person> getPersonComparator(String[] personFields) {
        return Arrays.stream(personFields)
                .map(String::toLowerCase)
                .map(SortPersonCommandParser::fieldToKey)
                .map(Comparator::comparing)
                .reduce(Comparator::thenComparing) // chain comparators
                .orElse(null); // return null if no valid fields
    }

    private static Function<Person, String> fieldToKey(String field) {
        return switch (field.toLowerCase()) {
        case "n/" -> p -> p.getName().toString();
        case "p/" -> p -> p.getPhone().toString();
        case "e/" -> p -> p.getEmail().toString();
        case "a/" -> p -> p.getAddress().toString();
        default -> p -> "";
        };
    }
}
