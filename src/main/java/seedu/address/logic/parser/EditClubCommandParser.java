package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditClubCommand;
import seedu.address.logic.commands.EditClubCommand.EditClubDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new EditClubCommand object.
 * Compulsory Name/Email check is deferred to execute() after index validation.
 */
public class EditClubCommandParser implements Parser<EditClubCommand> {

    @Override
    public EditClubCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditClubCommand.MESSAGE_USAGE), pe);
        }

        EditClubDescriptor editClubDescriptor = new EditClubDescriptor();

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            String raw = argMultimap.getValue(PREFIX_NAME).get().trim();
            if (raw.isEmpty()) {
                editClubDescriptor.setNameEmptyFlag(true);
            } else {
                editClubDescriptor.setName(ParserUtil.parseName(raw));
            }
        }

        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            String raw = argMultimap.getValue(PREFIX_PHONE).get().trim();
            if (!raw.isEmpty()) {
                editClubDescriptor.setPhone(ParserUtil.parsePhone(raw));
            }
        }

        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            String raw = argMultimap.getValue(PREFIX_EMAIL).get().trim();
            if (raw.isEmpty()) {
                editClubDescriptor.setEmailEmptyFlag(true);
            } else {
                editClubDescriptor.setEmail(ParserUtil.parseEmail(raw));
            }
        }

        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            String raw = argMultimap.getValue(PREFIX_ADDRESS).get().trim();
            if (!raw.isEmpty()) {
                editClubDescriptor.setAddress(ParserUtil.parseAddress(raw));
            }
        }

        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editClubDescriptor::setTags);

        if (!editClubDescriptor.isAnyFieldEdited()) {
            throw new ParseException("At least one field to edit must be provided.");
        }

        return new EditClubCommand(index, editClubDescriptor);
    }

    /**
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws ParseException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }
}
