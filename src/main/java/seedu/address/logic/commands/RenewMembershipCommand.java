package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLUB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DURATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEMBER;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.club.Club;
import seedu.address.model.person.Person;

/**
 * Renews a person's membership in a club by extending the expiry date by the specified duration in months.
 */
public class RenewMembershipCommand extends Command {

    public static final String COMMAND_WORD = "renew";
    public static final String MESSAGE_RENEWED_MEMBERSHIP =
            "%1$s membership in %2$s renewed. Extended by %3$s months.";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Renews a person's membership in a club "
            + "by extending the expiry date by the specified duration in months.\n"
            + "Parameters: "
            + PREFIX_MEMBER + "PERSON_INDEX "
            + PREFIX_CLUB + "CLUB_INDEX "
            + PREFIX_DURATION + "Duration in months (must be between 1 and 24)\n"
            + "Example: " + COMMAND_WORD + " m/1 c/2 d/12";

    private final Index personIndex;
    private final Index clubIndex;
    private final int durationInMonths;

    /**
     * @param personIndex Index of the person in the filtered person list
     * @param clubIndex Index of the club in the filtered club list
     * @param durationInMonths Duration in months to extend the membership
     */
    public RenewMembershipCommand(Index personIndex, Index clubIndex, int durationInMonths) {
        requireAllNonNull(personIndex, clubIndex, durationInMonths);
        this.personIndex = personIndex;
        this.clubIndex = clubIndex;
        this.durationInMonths = durationInMonths;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {

        List<Person> lastShownPersonList = model.getFilteredPersonList();
        List<Club> lastShownClubList = model.getFilteredClubList();

        if (personIndex.getZeroBased() >= lastShownPersonList.size()) {
            throw new CommandException(
                    String.format(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX_DETAILED, personIndex.getOneBased()));
        }
        if (clubIndex.getZeroBased() >= lastShownClubList.size()) {
            throw new CommandException(
                    String.format(Messages.MESSAGE_INVALID_CLUB_DISPLAYED_INDEX_DETAILED, clubIndex.getOneBased()));
        }
        Person personToRenew = lastShownPersonList.get(personIndex.getZeroBased());
        Club clubToRenew = lastShownClubList.get(clubIndex.getZeroBased());
        // Catch out of range duration
        try {
            model.renewMembership(personToRenew, clubToRenew, durationInMonths);
        } catch (IllegalArgumentException e) {
            throw new CommandException(e.getMessage());
        }
        return new CommandResult(String.format(
                MESSAGE_RENEWED_MEMBERSHIP,
                personToRenew.getName(),
                clubToRenew.getName(),
                durationInMonths));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RenewMembershipCommand)) {
            return false;
        }

        RenewMembershipCommand otherRenewCommand = (RenewMembershipCommand) other;
        return personIndex.equals(otherRenewCommand.personIndex)
                && clubIndex.equals(otherRenewCommand.clubIndex)
                && durationInMonths == otherRenewCommand.durationInMonths;
    }
}
