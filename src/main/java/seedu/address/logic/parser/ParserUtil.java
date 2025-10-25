package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.field.Address;
import seedu.address.model.field.Email;
import seedu.address.model.field.Name;
import seedu.address.model.field.Phone;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * stripped.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String strippedIndex = oneBasedIndex.strip();
        if (!StringUtil.isNonZeroUnsignedInteger(strippedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(strippedIndex));
    }

    /**
     * Parses {@code oneBasedIndexes} into an array of {@code Index} and returns it.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if any of the specified indexes is invalid (not non-zero unsigned integer).
     */
    public static Index[] parseIndexes(String oneBasedIndexes) throws ParseException {
        requireNonNull(oneBasedIndexes);
        String[] splitIndexes = oneBasedIndexes.trim().split("\\s+");
        List<Index> indexes = new ArrayList<>();
        Set<Index> uniqueIndexes = new HashSet<>();
        for (int i = 0; i < splitIndexes.length; i++) {
            Index index = parseIndex(splitIndexes[i]);
            if (uniqueIndexes.contains(index)) {
                continue;
            }
            uniqueIndexes.add(index);
            indexes.add(index);
        }
        return indexes.toArray(new Index[0]); // to avoid class cast exception
    }
    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be stripped.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String strippedName = name.strip();
        if (!Name.isValidName(strippedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(strippedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be stripped.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String strippedPhone = phone.strip();
        if (!Phone.isValidPhone(strippedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(strippedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be stripped.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        final String trimmed = address.trim();

        if (trimmed.isEmpty()) {
            return new Address("");
        }

        if (!Address.isValidAddress(trimmed)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }

        return new Address(trimmed);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be stripped.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String strippedEmail = email.strip();
        if (!Email.isValidEmail(strippedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(strippedEmail);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be stripped.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String strippedTag = tag.strip();
        if (!Tag.isValidTagName(strippedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(strippedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
            if (tagSet.size() > 5) {
                throw new ParseException("The number of tags cannot exceed 5.");
            }

            if (tagName.length() > 20) {
                throw new ParseException("Each tag should not be longer than 20 characters.");
            }
        }

        return tagSet;
    }
}
