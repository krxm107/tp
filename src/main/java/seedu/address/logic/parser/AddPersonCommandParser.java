package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLUB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddPersonCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.field.Address;
import seedu.address.model.field.Email;
import seedu.address.model.field.Name;
import seedu.address.model.field.Phone;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new {@link AddPersonCommand} object.
 *
 * <p>
 * The {@code p/PHONE} prefix is optional. If omitted, the created {@code Person}
 * will have an empty {@code Phone} instance.
 * </p>
 *
 * <p>
 * The {@code a/ADDRESS} prefix is optional. If omitted, the created {@code Person}
 * will have an empty {@code Address} instance.
 * </p>
 */
public class AddPersonCommandParser implements Parser<AddPersonCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddPersonCommand
     * and returns an AddPersonCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public AddPersonCommand parse(String args) throws ParseException {
        requireNonNull(args);

        // include PREFIX_PHONE in tokenize so it can be parsed if present
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(
                args, PREFIX_CLUB, PREFIX_NAME, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_PHONE, PREFIX_TAG);

        // ✅ Only these are required: NAME, EMAIL
        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_EMAIL)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPersonCommand.MESSAGE_USAGE));
        }

        // duplicates check is fine even if phone is absent
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_CLUB, PREFIX_NAME, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_PHONE);

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());

        // Address is optional. If the user supplies `p/` with no value, it is treated as absent.
        final String rawAddress = argMultimap.getValue(PREFIX_ADDRESS).orElse(null);

        // AddPersonCommandParser.java
        Address address = null;
        try {
            address = argMultimap.getValue(PREFIX_ADDRESS)
                .map(value -> {
                    try {
                        return ParserUtil.parseAddress(value); // "" accepted, invalid non-empty → ParseException
                    } catch (ParseException e) {
                        // bubble up as ParseException so the user sees the warning
                        throw new RuntimeException(e);
                    }
                })
                .orElse(new Address("")); // prefix absent → optional empty

        } catch (RuntimeException re) {
            if (re.getCause() instanceof ParseException) {
                throw (ParseException) re.getCause();
            }
            throw re;
        }

        // Phone is optional. If the user supplies `p/` with no value, it is treated as absent.
        final String rawPhone = argMultimap.getValue(PREFIX_PHONE).orElse(null);

        final Phone phone = (rawPhone == null || rawPhone.strip().isEmpty())
                ? new Phone("") // optional / absent phone
                : ParserUtil.parsePhone(rawPhone); // validate only if non-empty

        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        Person person = new Person(name, phone, email, address, tagList);

        // Add memberships to clubs if present
        if (argMultimap.getValue(PREFIX_CLUB).isPresent()) {
            Index[] clubIndexes = ParserUtil.parseIndexes(argMultimap.getValue(PREFIX_CLUB).get());
            return new AddPersonCommand(person, clubIndexes);
        }
        return new AddPersonCommand(person);
    }

    /**
     * Returns true if all the given prefixes are present in the given ArgumentMultimap.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
