package seedu.address.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.club.Club;
import seedu.address.model.person.Person;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    // Todo: format these strings to print the person/club's index number too
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid";
    public static final String MESSAGE_INVALID_CLUB_DISPLAYED_INDEX = "The club index provided is invalid";
    // Note: for now, we have this temp messages for invalid index
    // we should update the above 2 messages later and other commands which use those messages.
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX_DETAILED = "%1$d is an invalid person index";
    public static final String MESSAGE_INVALID_CLUB_DISPLAYED_INDEX_DETAILED = "%1$d is an invalid club index";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_CLUBS_LISTED_OVERVIEW = "%1$d clubs listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";

    /**
     * Returns an error message indicating the duplicate prefixes.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        assert duplicatePrefixes.length > 0;

        Set<String> duplicateFields =
                Stream.of(duplicatePrefixes).map(Prefix::toString).collect(Collectors.toSet());

        return MESSAGE_DUPLICATE_FIELDS + String.join(" ", duplicateFields);
    }

    /**
     * Formats the {@code person} for display to the user.
     */
    public static String format(Person person) {
        final StringBuilder builder = new StringBuilder();
        builder.append(person.getName())
                .append("; Phone: ")
                .append(person.getPhone())
                .append("; Email: ")
                .append(person.getEmail())
                .append("; Address: ")
                .append(person.getAddress())
                .append("; Tags: ");
        person.getTags().forEach(builder::append);
        return builder.toString();
    }

    /**
     * Formats the {@code club} for display to the user.
     */
    public static String format(Club club) {
        final StringBuilder builder = new StringBuilder();
        builder.append(club.getName())
                .append("; Phone: ")
                .append(club.getPhone())
                .append("; Email: ")
                .append(club.getEmail())
                .append("; Address: ")
                .append(club.getAddress())
                .append("; Tags: ");
        club.getTags().forEach(builder::append);
        return builder.toString();
    }

}
