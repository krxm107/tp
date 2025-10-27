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
import seedu.address.logic.commands.EditPersonCommand;
import seedu.address.logic.commands.EditPersonCommand.EditPersonDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new EditPersonCommand object.
 * NOTE: We do NOT throw “Name and email are compulsory” here; that is enforced in execute()
 * after the index is validated so that invalid index wins.
 */
public class EditPersonCommandParser implements Parser<EditPersonCommand> {

    @Override
    public EditPersonCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditPersonCommand.MESSAGE_USAGE), pe);
        }

        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();

        // Only parse when value is non-empty. If present but empty, set the empty-flag.
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            String raw = argMultimap.getValue(PREFIX_NAME).get().trim();
            if (raw.isEmpty()) {
                editPersonDescriptor.setNameEmptyFlag(true);
            } else {
                editPersonDescriptor.setName(ParserUtil.parseName(raw));
            }
        }

        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            String raw = argMultimap.getValue(PREFIX_PHONE).get().trim();
            if (!raw.isEmpty()) {
                editPersonDescriptor.setPhone(ParserUtil.parsePhone(raw));
            }
        }

        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            String raw = argMultimap.getValue(PREFIX_EMAIL).get().trim();
            if (raw.isEmpty()) {
                editPersonDescriptor.setEmailEmptyFlag(true);
            } else {
                editPersonDescriptor.setEmail(ParserUtil.parseEmail(raw));
            }
        }

        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            String raw = argMultimap.getValue(PREFIX_ADDRESS).get().trim();
            if (!raw.isEmpty()) {
                editPersonDescriptor.setAddress(ParserUtil.parseAddress(raw));
            }
        }

        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editPersonDescriptor::setTags);

        if (!editPersonDescriptor.isAnyFieldEdited()) {
            // Keep the project’s existing error message when literally nothing is provided.
            throw new ParseException("At least one field to edit must be provided.");
        }

        return new EditPersonCommand(index, editPersonDescriptor);
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
