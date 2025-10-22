package seedu.address.logic.parser.get;

import static seedu.address.logic.Messages.format;

import seedu.address.model.club.Club;
import seedu.address.model.membership.Membership;

/**
 * Parser for the string to be used in a GetClubCommand.
 */
public class GetClubMessageParser {

    /**
     * Parses a {@code club} into an appropriate string to be copied to the user's clipboard.
     * {@code args} is used to specify what details to be included.
     */
    public String parse(Club club, String args) {
        if (args.isEmpty()) {
            return format(club);
        } else if (args.contains("*")) {
            return getFullClubDetails(club);
        } else {
            return getClubDetails(club, args);
        }
    }

    private String getClubDetails(Club club, String args) {
        StringBuilder sb = new StringBuilder();
        if (args.contains("n")) {
            sb.append(club.getName()).append(" ");
        }
        if (args.contains("p")) {
            sb.append(club.getPhone()).append(" ");
        }
        if (args.contains("e")) {
            sb.append(club.getEmail()).append(" ");
        }
        if (args.contains("a")) {
            sb.append(club.getAddress()).append(" ");
        }
        if (args.contains("m")) {
            club.getMemberships().stream().forEach(membership ->
                    sb.append(membership.getPersonName()).append(" "));
        }
        return sb.toString();
    }

    private String getFullClubDetails(Club club) {
        StringBuilder sb = new StringBuilder(format(club));
        for (Membership memberships : club.getMemberships()) {
            sb.append("\n").append(format(memberships.getPerson()));
        }
        return sb.toString();
    }

}
