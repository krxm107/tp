package seedu.address.logic.parser;

import seedu.address.logic.commands.AddToCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.club.Club;
import seedu.address.model.field.Email;
import seedu.address.model.field.Name;
import seedu.address.model.field.Phone;
import seedu.address.model.person.Person;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLUB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

public class AddToCommandParser implements Parser<AddToCommand> {
    public AddToCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_CLUB, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL);

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_CLUB, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL);
        Name personName = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Name clubName = ParserUtil.parseName(argMultimap.getValue(PREFIX_CLUB).get());

        return new AddToCommand(personName, clubName);
    }

}
