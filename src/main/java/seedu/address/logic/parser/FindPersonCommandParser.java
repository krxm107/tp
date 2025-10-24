package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.commands.FindPersonCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.search.CombinedSearchPredicate;
import seedu.address.logic.search.parsers.AddressParser;
import seedu.address.logic.search.parsers.EmailParser;
import seedu.address.logic.search.parsers.NameParser;
import seedu.address.logic.search.parsers.PhoneParser;
import seedu.address.logic.search.parsers.TagParser;
import seedu.address.model.person.Person;

/**
 * Parses input arguments and creates a new FindPersonCommand object
 */
public class FindPersonCommandParser implements Parser<FindPersonCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindPersonCommand
     * and returns a FindPersonCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindPersonCommand parse(String args) throws ParseException {
        CombinedSearchPredicate<Person> predicate = new CombinedSearchPredicate<>();

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(
                args, PREFIX_NAME, PREFIX_TAG, PREFIX_ADDRESS, PREFIX_EMAIL, PREFIX_PHONE);

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindPersonCommand.MESSAGE_USAGE));
        }

        for (String prefix : argMultimap.getAllValues(PREFIX_NAME)) {
            predicate.add(new NameParser<Person>().parse(prefix));
        }
        for (String prefix : argMultimap.getAllValues(PREFIX_TAG)) {
            predicate.add(new TagParser<Person>().parse(prefix));
        }
        for (String prefix : argMultimap.getAllValues(PREFIX_ADDRESS)) {
            predicate.add(new AddressParser<Person>().parse(prefix));
        }
        for (String prefix : argMultimap.getAllValues(PREFIX_EMAIL)) {
            predicate.add(new EmailParser<Person>().parse(prefix));
        }
        for (String prefix : argMultimap.getAllValues(PREFIX_PHONE)) {
            predicate.add(new PhoneParser<Person>().parse(prefix));
        }

        return new FindPersonCommand(predicate);
    }

}
