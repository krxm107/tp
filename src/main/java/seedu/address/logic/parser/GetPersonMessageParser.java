package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.ADDRESS;
import static seedu.address.logic.parser.CliSyntax.EMAIL;
import static seedu.address.logic.parser.CliSyntax.MEMBER;
import static seedu.address.logic.parser.CliSyntax.NAME;
import static seedu.address.logic.parser.CliSyntax.PHONE;

import java.util.function.Function;

import seedu.address.logic.commands.GetPersonCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Person;

/**
 * Parser for the string to be used in a GetPersonCommand.
 */
public class GetPersonMessageParser {

    /**
     * Returns a <code>Function</code> that maps a person to the appropriate details to be copied.
     * {@code args} is used to specify what details to be included.
     */
    public static Function<Person, String> parse(String args) throws ParseException {
        args = args.trim().toLowerCase();
        return switch (args) {
        case NAME -> person -> person.getName().fullName;
        case PHONE -> person -> person.getPhone().value;
        case EMAIL -> person -> person.getEmail().value;
        case ADDRESS -> person -> person.getAddress().value;
        case MEMBER -> GetPersonMessageParser::getPersonMemberships;
        default -> throw new ParseException(String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, GetPersonCommand.MESSAGE_USAGE));
        };
    }

    private static String getPersonMemberships(Person person) {
        StringBuilder sb = new StringBuilder(person.getName().fullName).append(": ");
        person.getMemberships().forEach(membership -> sb.append("\n").append(membership.getClubName()));
        return sb.toString();
    }

}
