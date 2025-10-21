package seedu.address.logic.parser.get;

import static seedu.address.logic.Messages.format;

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
            return getPersonDetails(person, args);
        }
    }

    private String getPersonDetails(Person person, String args) {
        StringBuilder sb = new StringBuilder();
        if (args.contains("n")) {
            sb.append(person.getName()).append(" ");
        }
        if (args.contains("p")) {
            sb.append(person.getPhone()).append(" ");
        }
        if (args.contains("e")) {
            sb.append(person.getEmail()).append(" ");
        }
        if (args.contains("a")) {
            sb.append(person.getAddress()).append(" ");
        }
        if (args.contains("m")) {
            person.getMemberships().stream().forEach(membership ->
                    sb.append(membership.getClubName()).append(" "));
        }
        return sb.toString();
    }

}
