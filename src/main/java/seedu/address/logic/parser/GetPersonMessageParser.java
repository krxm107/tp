package seedu.address.logic.parser;

import static seedu.address.logic.Messages.format;
import static seedu.address.logic.parser.CliSyntax.ADDRESS;
import static seedu.address.logic.parser.CliSyntax.EMAIL;
import static seedu.address.logic.parser.CliSyntax.MEMBER;
import static seedu.address.logic.parser.CliSyntax.NAME;
import static seedu.address.logic.parser.CliSyntax.PHONE;

import seedu.address.model.person.Person;

/**
 * Parser for the string to be used in a GetPersonCommand.
 */
public class GetPersonMessageParser {

    /**
     * Parses a {@code person} into an appropriate string to be copied to the user's clipboard.
     * {@code args} is used to specify what details to be included.
     */
    public String parse(Person person, String args) {
        if (args.isEmpty()) {
            return format(person);
        } else {
            return getPersonDetails(person, args.toLowerCase());
        }
    }

    private String getPersonDetails(Person person, String args) {
        StringBuilder sb = new StringBuilder();
        if (args.contains(NAME)) {
            sb.append(person.getName()).append(" ");
        }
        if (args.contains(PHONE)) {
            sb.append(person.getPhone()).append(" ");
        }
        if (args.contains(EMAIL)) {
            sb.append(person.getEmail()).append(" ");
        }
        if (args.contains(ADDRESS)) {
            sb.append(person.getAddress()).append(" ");
        }
        if (args.contains(MEMBER)) {
            person.getMemberships().stream().forEach(membership ->
                    sb.append(membership.getClubName()).append("; "));
        }
        return sb.toString();
    }

}
