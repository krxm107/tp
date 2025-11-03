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
public class DeleteMembershipCommand extends Command {
    public static final String COMMAND_WORD = "delete_membership";
    public static final String COMMAND_SHORT = "deletem"; // delete membership
    public static final String MESSAGE_REMOVED_FROM_CLUB = "%1$s removed from %2$s";
    public static final String MESSAGE_USAGE = COMMAND_WORD + " (" + COMMAND_SHORT
            + "): Removes a person from a club\n"
            + "Parameters: \n"
            + PREFIX_MEMBER + "INDEXES (space-separated positive integers)\n"
            + PREFIX_CLUB + "INDEXES (space-separated positive integers)\n"
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
    public DeleteMembershipCommand(Index[] personIndexes, Index[] clubIndexes) {
        requireAllNonNull(personIndexes, clubIndexes);
        this.personIndexes = personIndexes;
        this.clubIndexes = clubIndexes;
    }

    private void appendToMessage(StringBuilder builder, String message, Object... args) {
        builder.append(String.format(message, args)).append("\n");
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Person> lastShownPersonList = model.getFilteredPersonList();
        List<Club> lastShownClubList = model.getFilteredClubList();
        StringBuilder outputMessageBuilder = new StringBuilder();

        List<Index> invalidPersons = findInvalidIndexes(personIndexes, lastShownPersonList.size());
        List<Index> invalidClubs = findInvalidIndexes(clubIndexes, lastShownClubList.size());

        for (Index idx : invalidPersons) {
            appendToMessage(outputMessageBuilder,
                    Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX_DETAILED, idx.getOneBased());
        }
        for (Index idx : invalidClubs) {
            appendToMessage(outputMessageBuilder,
                    Messages.MESSAGE_INVALID_CLUB_DISPLAYED_INDEX_DETAILED, idx.getOneBased());
        }

        List<Index> validPersons = findValidIndexes(personIndexes, lastShownPersonList.size());
        List<Index> validClubs = findValidIndexes(clubIndexes, lastShownClubList.size());

        for (Index clubIndex : validClubs) {
            StringBuilder personNamesBuilder = new StringBuilder();
            Club club = lastShownClubList.get(clubIndex.getZeroBased());
            String clubName = club.getName().toString();

            for (Index personIndex : validPersons) {
                Person person = lastShownPersonList.get(personIndex.getZeroBased());
                String personName = person.getName().toString();

                // Check if membership doesn't exist
                Membership toRemove = new Membership(person, club);
                if (!model.hasMembership(toRemove)) {
                    appendToMessage(outputMessageBuilder, MESSAGE_NOTEXIST_MEMBERSHIP, personName, clubName);
                    continue; // Skip to the next person index
                }
                // Only add the person who are in the club to personNamesBuilder
                // for MESSAGE_REMOVED_FROM_CLUB message
                personNamesBuilder.append(personName).append(", ");

                club.removeMember(person);
                person.removeClub(club);
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
            appendToMessage(outputMessageBuilder, MESSAGE_REMOVED_FROM_CLUB, personNames, clubName);
        }
        String outputMessage = outputMessageBuilder.toString();
        return new CommandResult(outputMessage);
    }

    private List<Index> findInvalidIndexes(Index[] indexes, int size) {
        return Arrays.stream(indexes)
                .filter(i -> i.getZeroBased() >= size)
                .toList();
    }

    private List<Index> findValidIndexes(Index[] indexes, int size) {
        return Arrays.stream(indexes)
                .filter(i -> i.getZeroBased() < size)
                .toList();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteMembershipCommand)) {
            return false;
        }

        DeleteMembershipCommand otherDeleteMembershipCommand = (DeleteMembershipCommand) other;
        return Arrays.equals(personIndexes, otherDeleteMembershipCommand.personIndexes)
                && Arrays.equals(clubIndexes, otherDeleteMembershipCommand.clubIndexes);
    }
}
