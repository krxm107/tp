package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Function;

import seedu.address.logic.commands.SortClubCommand;
import seedu.address.logic.commands.SortPersonCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.club.Club;

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

    private static Function<Club, String> fieldToKey(String field) {
        return switch (field.toLowerCase()) {
        case "n/" -> c -> c.getName().toString();
        case "p/" -> c -> c.getPhone().toString();
        case "e/" -> c -> c.getEmail().toString();
        case "a/" -> c -> c.getAddress().toString();
        default -> c -> "";
        };
    }
}
