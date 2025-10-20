package seedu.address.logic.commands;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.club.Club;
import seedu.address.model.person.Person;

import java.util.List;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLUB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DURATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEMBER;

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
            + PREFIX_DURATION + "DURATION_IN_MONTHS\n"
            + "Example: " + COMMAND_WORD + " m/1 c/2 d/12";

    private final Index personIndex;
    private final Index clubIndex;
    private final int durationInMonths;

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
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        if (clubIndex.getZeroBased() >= lastShownClubList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CLUB_DISPLAYED_INDEX);
        }
        Person personToRenew = lastShownPersonList.get(personIndex.getZeroBased());
        Club clubToRenew = lastShownClubList.get(clubIndex.getZeroBased());
        model.renewMembership(personToRenew, clubToRenew, durationInMonths);
        return new CommandResult(String.format(
                MESSAGE_RENEWED_MEMBERSHIP,
                personToRenew.getName(),
                clubToRenew.getName(),
                durationInMonths));
    }
}
