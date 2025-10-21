package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLUB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEMBER;

import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.club.Club;
import seedu.address.model.membership.Membership;
import seedu.address.model.person.Person;

/**
 * Removes a Person from a Club
 */
public class RemoveFromCommand extends Command {
    public static final String COMMAND_WORD = "remove_from";
    public static final String MESSAGE_REMOVED_FROM_CLUB = "%1$s removed from %2$s";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes a person from a club\n"
            + "Person identified by the index number used in the displayed person list.\n"
            + "Club identified by the index number used in the displayed person list.\n"
            + "Parameters: "
            + PREFIX_MEMBER + "Person INDEXes (must be positive integers separated by space)\n"
            + PREFIX_CLUB + "Club INDEXes (must be positive integers separated by space)\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_MEMBER + "1 2 4 "
            + PREFIX_CLUB + "1 3";

    public static final String MESSAGE_NOTEXIST_MEMBERSHIP = "%1$s is NOT in %2$s";
    private final Index[] personIndexes;
    private final Index[] clubIndexes;

    /**
     * @param personIndexes of the person in the filtered person list to edit
     * @param clubIndexes of the club in the filtered club list to edit
     */
    public RemoveFromCommand(Index[] personIndexes, Index[] clubIndexes) {
        requireAllNonNull(personIndexes, clubIndexes);
        this.personIndexes = personIndexes;
        this.clubIndexes = clubIndexes;
    }

    private void concatInvalidIndexMessage(
            StringBuilder builder, boolean isPerson, boolean isFirstClubIndex, Index index) {
        if (isPerson && isFirstClubIndex) {
            builder
                    .append(String.format(
                            Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX_DETAILED, index.getOneBased()))
                    .append("\n");
        } else if (!isPerson) {
            builder
                    .append(String.format(
                            Messages.MESSAGE_INVALID_CLUB_DISPLAYED_INDEX_DETAILED, index.getOneBased()))
                    .append("\n");
        }
    }

    private void concatNotExistInClubMessage(StringBuilder builder, String personNames, String clubName) {
        builder.append(String.format(MESSAGE_NOTEXIST_MEMBERSHIP, personNames, clubName)).append("\n");
    }

    private void concatRemovedFromClubMessage(StringBuilder builder, String personNames, String clubName) {
        builder.append(String.format(MESSAGE_REMOVED_FROM_CLUB, personNames, clubName)).append("\n");
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Person> lastShownPersonList = model.getFilteredPersonList();
        List<Club> lastShownClubList = model.getFilteredClubList();
        StringBuilder outputMessageBuilder = new StringBuilder();

        for (Index clubIndex : clubIndexes) {
            StringBuilder personNamesBuilder = new StringBuilder();
            boolean isFirstClubIndex = (clubIndex == clubIndexes[0]);
            if (clubIndex.getZeroBased() >= lastShownClubList.size()) {
                concatInvalidIndexMessage(outputMessageBuilder, false, isFirstClubIndex, clubIndex);
                continue; // Skip to the next club index
            }
            Club club = lastShownClubList.get(clubIndex.getZeroBased());
            String clubName = club.getName().toString();
            for (Index personIndex : personIndexes) {
                if (personIndex.getZeroBased() >= lastShownPersonList.size()) {
                    // Add invalid person index message once only for each invalid person index
                    // Achieved by only adding when processing the first club index
                    concatInvalidIndexMessage(outputMessageBuilder, true, isFirstClubIndex, personIndex);
                    continue; // Skip to the next club index
                }
                Person person = lastShownPersonList.get(personIndex.getZeroBased());
                String personName = person.getName().toString();

                // Check if membership doesn't exist
                Membership toRemove = new Membership(person, club);
                if (!model.hasMembership(toRemove)) {
                    concatNotExistInClubMessage(outputMessageBuilder, personName, clubName);
                    continue; // Skip to the next person index
                }
                // Only add the person who are in the club to personNamesBuilder
                // for MESSAGE_REMOVED_FROM_CLUB message
                personNamesBuilder.append(personName).append(", ");

                club.removeMember(person);
                // Model also keep track of memberships
                model.deleteMembership(toRemove);
            }
            // Also handle the case where no new memberships were added
            if (personNamesBuilder.length() == 0) {
                continue; // No new members were added to this club
            }
            // Remove the trailing comma and space
            assert personNamesBuilder.length() >= 2;
            personNamesBuilder.setLength(personNamesBuilder.length() - 2);
            String personNames = personNamesBuilder.toString();
            concatRemovedFromClubMessage(outputMessageBuilder, personNames, clubName);
        }
        String outputMessage = outputMessageBuilder.toString();
        return new CommandResult(outputMessage);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RemoveFromCommand)) {
            return false;
        }

        RemoveFromCommand otherRemoveFromCommand = (RemoveFromCommand) other;
        return Arrays.equals(personIndexes, otherRemoveFromCommand.personIndexes)
                && Arrays.equals(clubIndexes, otherRemoveFromCommand.clubIndexes);
    }
}
