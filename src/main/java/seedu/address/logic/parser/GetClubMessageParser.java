package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.format;
import static seedu.address.logic.parser.CliSyntax.ADDRESS;
import static seedu.address.logic.parser.CliSyntax.EMAIL;
import static seedu.address.logic.parser.CliSyntax.MEMBER;
import static seedu.address.logic.parser.CliSyntax.NAME;
import static seedu.address.logic.parser.CliSyntax.PHONE;
import static seedu.address.model.membership.MembershipStatus.CANCELLED;

import java.util.function.Function;

import seedu.address.logic.commands.GetClubCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.club.Club;
import seedu.address.model.membership.Membership;

/**
 * Parser for the string to be used in a GetClubCommand.
 */
public class GetClubMessageParser {

    /**
     * Returns a <code>Function</code> that maps a club to the appropriate details to be copied.
     * {@code args} is used to specify what details to be included.
     */
    public static Function<Club, String> parse(String args) throws ParseException {
        args = args.trim().toLowerCase();
        return switch (args) {
            case "*" -> GetClubMessageParser::getFullClubDetails;
            case NAME -> club -> club.getName().fullName;
            case PHONE -> club -> club.getPhone().value;
            case EMAIL -> club -> club.getEmail().value;
            case ADDRESS -> club -> club.getAddress().value;
            case MEMBER -> GetClubMessageParser::getClubMemberships;
            default -> throw new ParseException(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, GetClubCommand.MESSAGE_USAGE));
        };
    }

    private static String getClubMemberships(Club club) {
        StringBuilder sb = new StringBuilder(club.getName().fullName).append(": ");
        club.getMemberships().forEach(membership -> sb.append("\n").append(membership.getPersonName()));
        return sb.toString();
    }

    private static String getFullClubDetails(Club club) {
        StringBuilder sb = new StringBuilder(format(club));
        for (Membership memberships : club.getMemberships()) {
            if (!memberships.getStatus().equals(CANCELLED)) {
                sb.append("\n").append(format(memberships.getPerson()));
            }
        }
        return sb.toString();
    }

}
