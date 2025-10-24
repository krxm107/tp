package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.address.logic.commands.AddClubCommand;
import seedu.address.logic.commands.EditClubCommand.EditClubDescriptor;
import seedu.address.model.club.Club;
import seedu.address.model.tag.Tag;

/**
 * A utility class for Club.
 */
public class ClubUtil {

    /**
     * Returns an add command string for adding the {@code club}.
     */
    public static String getAddCommand(Club club) {
        return AddClubCommand.COMMAND_WORD + " " + getClubDetails(club);
    }

    /**
     * Returns the part of command string for the given {@code club}'s details.
     */
    public static String getClubDetails(Club club) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + club.getName().fullName + " ");
        sb.append(PREFIX_PHONE + club.getPhone().value + " ");
        sb.append(PREFIX_EMAIL + club.getEmail().value + " ");
        sb.append(PREFIX_ADDRESS + club.getAddress().value + " ");
        club.getTags().stream().forEach(
                s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditClubDescriptor}'s details.
     */
    public static String getEditClubDescriptorDetails(EditClubDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.value).append(" "));
        descriptor.getEmail().ifPresent(email -> sb.append(PREFIX_EMAIL).append(email.value).append(" "));
        descriptor.getAddress().ifPresent(address -> sb.append(PREFIX_ADDRESS).append(address.value).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }
}
