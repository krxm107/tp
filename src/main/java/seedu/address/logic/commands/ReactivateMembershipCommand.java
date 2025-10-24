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
 * Reactivates a person's membership in a club by extending the expiry date by the specified duration in months.
 */
public class ReactivateMembershipCommand extends Command {

    public static final String COMMAND_WORD = "reactivate";
    public static final String MESSAGE_REACTIVATED_MEMBERSHIP =
            "%1$s membership in %2$s reactivated. Extended by %3$s months.";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Reactivates a person's membership in a club "
            + "by extending the expiry date by the specified duration in months.\n"
            + "If expiry date was in the past, setting new expiry date from today.\n"
            + "If expiry date was in the future, extending from current expiry date.\n"
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
    public ReactivateMembershipCommand(Index personIndex, Index clubIndex, int durationInMonths) {
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
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        if (clubIndex.getZeroBased() >= lastShownClubList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CLUB_DISPLAYED_INDEX);
        }
        Person personToReactivate = lastShownPersonList.get(personIndex.getZeroBased());
        Club clubToReactivate = lastShownClubList.get(clubIndex.getZeroBased());
        // Catch out of range duration
        try {
            model.reactivateMembership(personToReactivate, clubToReactivate, durationInMonths);
        } catch (IllegalArgumentException e) {
            throw new CommandException(e.getMessage());
        }
        return new CommandResult(String.format(
                MESSAGE_REACTIVATED_MEMBERSHIP,
                personToReactivate.getName(),
                clubToReactivate.getName(),
                durationInMonths));
    }
}
