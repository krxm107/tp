package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLUB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DURATION;
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
 * Adds a Person to a Club
 */
public class AddToCommand extends Command {
    public static final String COMMAND_WORD = "add_to";
    public static final String MESSAGE_ADDED_TO_CLUB = "%1$s added to %2$s";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds multiple persons to multiple clubs with membership duration\n"
            + "Person identified by the index number used in the displayed person list.\n"
            + "Club identified by the index number used in the displayed person list.\n"
            + "Parameters: "
            + PREFIX_MEMBER + "Person INDEXes (must be positive integers separated by space)\n"
            + PREFIX_CLUB + "Club INDEXes (must be positive integers separated by space)\n"
            + PREFIX_DURATION + "Duration in months (must be between 1 and 24, optional, default is 12)\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_MEMBER + "1 2 4 "
            + PREFIX_CLUB + "1 3 "
            + PREFIX_DURATION + "12";

    private static final String MESSAGE_DUPLICATE_MEMBERSHIP = "%1$s is already in %2$s";

    private final Index[] personIndexes;
    private final Index[] clubIndexes;
    private int durationInMonths = Membership.DEFAULT_DURATION_IN_MONTHS;

    /**
     * @param personIndexes of the person in the filtered person list to edit
     * @param clubIndexes of the club in the filtered club list to edit
     */
    public AddToCommand(Index[] personIndexes, Index[] clubIndexes) {
        requireAllNonNull(personIndexes, clubIndexes);
        this.personIndexes = personIndexes;
        this.clubIndexes = clubIndexes;
    }

    /**
     * @param personIndexes of the person in the filtered person list to edit
     * @param clubIndexes of the club in the filtered club list to edit
     * @param durationInMonths duration of membership in months
     */
    public AddToCommand(Index[] personIndexes, Index[] clubIndexes, int durationInMonths) {
        requireAllNonNull(personIndexes, clubIndexes, durationInMonths);
        this.personIndexes = personIndexes;
        this.clubIndexes = clubIndexes;
        this.durationInMonths = durationInMonths;
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

    private void concatDuplicateMembershipMessage(StringBuilder builder, String personName, String clubName) {
        builder.append(String.format(MESSAGE_DUPLICATE_MEMBERSHIP, personName, clubName)).append("\n");
    }

    private void concatAddedToClubMessage(StringBuilder builder, String personNames, String clubName) {
        builder.append(String.format(MESSAGE_ADDED_TO_CLUB, personNames, clubName)).append("\n");
    }

    private Membership createMembership(Person person, Club club) throws CommandException {
        try {
            return new Membership(person, club, durationInMonths);
        } catch (IllegalArgumentException e) {
            throw new CommandException(e.getMessage());
        }
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Person> lastShownPersonList = model.getFilteredPersonList();
        List<Club> lastShownClubList = model.getFilteredClubList();
        StringBuilder outputMessageBuilder = new StringBuilder();

        for (Index clubIndex : clubIndexes) {
            StringBuilder personNamesBuilder = new StringBuilder();
            boolean isFirstClubIndex = clubIndex == clubIndexes[0];
            if (clubIndex.getZeroBased() >= lastShownClubList.size()) {
                concatInvalidIndexMessage(outputMessageBuilder, false, isFirstClubIndex, clubIndex);
                continue; // Skip to the next club index
            }
            Club club = lastShownClubList.get(clubIndex.getZeroBased());
            String clubName = club.getName().toString();
            for (Index personIndex : personIndexes) {
                if (personIndex.getZeroBased() >= lastShownPersonList.size()) {
                    // Add invalid person index message once only per person index
                    // Achieved by only adding when processing the first club index
                    concatInvalidIndexMessage(outputMessageBuilder, true, isFirstClubIndex, personIndex);
                    continue; // Skip to the next club index
                }
                Person person = lastShownPersonList.get(personIndex.getZeroBased());
                String personName = person.getName().toString();

                // Check if membership already exists
                Membership toAdd = createMembership(person, club);
                if (model.hasMembership(toAdd)) {
                    concatDuplicateMembershipMessage(outputMessageBuilder, personName, clubName);
                    continue; //Skip adding this membership and move to the next person
                }
                //Only add the person who are not already in the club to personNamesBuilder
                // for MESSAGE_ADDED_TO_CLUB message
                personNamesBuilder.append(personName).append(", ");

                club.addMembership(toAdd);
                person.addMembership(toAdd);
                // model keep track of the generic membership without role. may change this later
                model.addMembership(toAdd);
            }
            // Also handle the case where no new memberships were added
            if (personNamesBuilder.length() == 0) {
                continue; // No new members were added to this club
            }
            // Remove the trailing comma and space
            assert personNamesBuilder.length() >= 2;
            personNamesBuilder.setLength(personNamesBuilder.length() - 2);
            String personNames = personNamesBuilder.toString();
            concatAddedToClubMessage(outputMessageBuilder, personNames, clubName);
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
        if (!(other instanceof AddToCommand)) {
            return false;
        }

        AddToCommand otherAddToCommand = (AddToCommand) other;
        return Arrays.equals(personIndexes, otherAddToCommand.personIndexes)
                && Arrays.equals(clubIndexes, otherAddToCommand.clubIndexes)
                && durationInMonths == otherAddToCommand.durationInMonths;
    }
}

