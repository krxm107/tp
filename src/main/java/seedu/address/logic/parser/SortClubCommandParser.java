package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Function;

import seedu.address.logic.commands.SortClubCommand;
import seedu.address.logic.commands.SortPersonCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.club.Club;
import seedu.address.model.person.Person;

/**
 * Parses input arguments and creates a new SortClubCommand object.
 */
public class SortClubCommandParser implements Parser<SortClubCommand> {

    @Override
    public SortClubCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {

            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortPersonCommand.MESSAGE_USAGE));
        }

        String[] clubFields = trimmedArgs.split("\\s+");

        return new SortClubCommand(getClubComparator(clubFields));
    }

    private Comparator<Club> getClubComparator(String[] clubFields) {
        return Arrays.stream(clubFields)
                .map(String::toLowerCase)
                .map(SortClubCommandParser::fieldToKey)
                .map(Comparator::comparing)
                .reduce(Comparator::thenComparing) // chain comparators
                .orElse(null); // return null if no valid fields
    }

    private static Function<Club, Comparable> fieldToKey(String field) {
        switch (field.toLowerCase()) {
        case "n/":
            return Club::getName;
        case "p/":
            return Club::getPhone;
        case "e/":
            return Club::getEmail;
        case "a/":
            return Club::getAddress;
        default:
            return p -> "";
        }
    }
}
