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
 * Adds 1 or more Persons to 1 or more Clubs
 */
public class AddMembershipCommand extends Command {
    public static final String COMMAND_WORD = "add_membership";
    public static final String COMMAND_SHORT = "addm"; // add member

    public static final String MESSAGE_ADDED_TO_CLUB = "%1$s added to %2$s";
    public static final String MESSAGE_USAGE = COMMAND_WORD + " (" + COMMAND_SHORT
            + "): Adds multiple persons to multiple clubs with membership duration\n"
            + "Parameters: \n"
            + PREFIX_MEMBER + "INDEXES (space-separated positive integers)\n"
            + PREFIX_CLUB + "INDEXES (space-separated positive integers)\n"
            + PREFIX_DURATION + "DURATION in months (between 1 and 24, optional, default is 12)\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_MEMBER + "1 2 4 "
            + PREFIX_CLUB + "1 3 "
            + PREFIX_DURATION + "12";

    public static final String MESSAGE_DUPLICATE_MEMBERSHIP = "%1$s is already in %2$s";

    private final Index[] personIndexes;
    private final Index[] clubIndexes;
    private int durationInMonths = Membership.DEFAULT_DURATION_IN_MONTHS;

    /**
     * @param personIndexes of the person in the filtered person list to edit
     * @param clubIndexes of the club in the filtered club list to edit
     */
    public AddMembershipCommand(Index[] personIndexes, Index[] clubIndexes) {
        requireAllNonNull(personIndexes, clubIndexes);
        this.personIndexes = personIndexes;
        this.clubIndexes = clubIndexes;
    }

    /**
     * @param personIndexes of the person in the filtered person list to edit
     * @param clubIndexes of the club in the filtered club list to edit
     * @param durationInMonths duration of membership in months
     */
    public AddMembershipCommand(Index[] personIndexes, Index[] clubIndexes, int durationInMonths) {
        requireAllNonNull(personIndexes, clubIndexes, durationInMonths);
        this.personIndexes = personIndexes;
        this.clubIndexes = clubIndexes;
        this.durationInMonths = durationInMonths;
    }

    private void appendToMessage(StringBuilder builder, String message, Object... args) {
        builder.append(String.format(message, args)).append("\n");
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

                // Check if membership already exists
                Membership toAdd = createMembership(person, club);
                if (model.hasMembership(toAdd)) {
                    appendToMessage(outputMessageBuilder, MESSAGE_DUPLICATE_MEMBERSHIP, personName, clubName);
                    continue; //Skip adding this membership and move to the next person
                }
                //Only add the person who are not already in the club to personNamesBuilder
                // for MESSAGE_ADDED_TO_CLUB message
                personNamesBuilder.append(personName).append(", ");

                club.addMembership(toAdd);
                person.addMembership(toAdd);
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
            appendToMessage(outputMessageBuilder, MESSAGE_ADDED_TO_CLUB, personNames, clubName);
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
        if (!(other instanceof AddMembershipCommand)) {
            return false;
        }

        AddMembershipCommand otherAddMembershipCommand = (AddMembershipCommand) other;
        return Arrays.equals(personIndexes, otherAddMembershipCommand.personIndexes)
                && Arrays.equals(clubIndexes, otherAddMembershipCommand.clubIndexes)
                && durationInMonths == otherAddMembershipCommand.durationInMonths;
    }
}
