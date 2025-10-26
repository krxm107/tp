package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Set;

import seedu.address.model.club.Club;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;


/**
 * Helper functions for handling strings.
 */
public class StringUtil {
    /**
     * Returns true if the {@code sentence} contains the {@code word}.
     *   Ignores case, but a full word match is required.
     *   <br>examples:<pre>
     *       containsWordIgnoreCase("ABc def", "abc") == true
     *       containsWordIgnoreCase("ABc def", "DEF") == true
     *       containsWordIgnoreCase("ABc def", "AB") == false //not a full word match
     *       </pre>
     * @param sentence cannot be null
     * @param word cannot be null, cannot be empty, must be a single word
     */
    public static boolean containsWordIgnoreCase(String sentence, String word) {
        requireNonNull(sentence);
        requireNonNull(word);

        String preppedWord = word.strip();
        checkArgument(!preppedWord.isEmpty(), "Word parameter cannot be empty");
        checkArgument(preppedWord.split("\\s+").length == 1, "Word parameter should be a single word");

        String preppedSentence = sentence;
        String[] wordsInPreppedSentence = preppedSentence.split("\\s+");

        return Arrays.stream(wordsInPreppedSentence)
                .anyMatch(preppedWord::equalsIgnoreCase);
    }

    /**
     * Returns true if the given person matches the keyword in any of their fields.
     */
    public static boolean matchPersonByKeyword(Person person, String keyword) {
        return matchAddress(person.getAddress().toString().toLowerCase(), keyword)
                || matchEmail(person.getEmail().toString().toLowerCase(), keyword)
                || matchName(person.getName().toString().toLowerCase(), keyword)
                || matchPhone(person.getPhone().toString().toLowerCase(), keyword)
                || matchTag(person.getTags(), keyword);
    }

    /**
     * Returns true if the given club matches the keyword in any of its fields.
     */
    public static boolean matchClubByKeyword(Club club, String keyword) {
        return matchAddress(club.getAddress().toString().toLowerCase(), keyword)
                || matchEmail(club.getEmail().toString().toLowerCase(), keyword)
                || matchName(club.getName().toString().toLowerCase(), keyword)
                || matchPhone(club.getPhone().toString().toLowerCase(), keyword)
                || matchTag(club.getTags(), keyword);
    }

    private static boolean matchAddress(String address, String keyword) {
        if (getPrefix(keyword).equals("a/")) {
            String description = getDescription(keyword);
            return address.contains(description);
        }

        return false;
    }

    private static boolean matchEmail(String email, String keyword) {
        if (getPrefix(keyword).equals("e/")) {
            String description = getDescription(keyword);
            return email.contains(description);
        }

        return false;
    }

    private static boolean matchName(String name, String keyword) {
        if (getPrefix(keyword).equals("n/")) {
            String description = getDescription(keyword);
            return name.contains(description);
        }

        return false;
    }

    private static boolean matchPhone(String phone, String keyword) {
        if (getPrefix(keyword).equals("p/")) {
            String description = getDescription(keyword);
            return phone.contains(description);
        }

        return false;
    }

    private static boolean matchTag(Set<Tag> tags, String keyword) {
        if (getPrefix(keyword).equals("t/")) {
            String description = getDescription(keyword);
            return tags.stream()
                    .anyMatch(tag -> tag.toString().toLowerCase().contains(description));
        }

        return false;
    }

    private static String getPrefix(String keyword) {
        return keyword.substring(0, 2).toLowerCase();
    }

    private static String getDescription(String keyword) {
        return keyword.substring(2).toLowerCase();
    }

    /**
     * Returns a detailed message of the t, including the stack trace.
     */
    public static String getDetails(Throwable t) {
        requireNonNull(t);
        StringWriter sw = new StringWriter();
        t.printStackTrace(new PrintWriter(sw));
        return t.getMessage() + "\n" + sw.toString();
    }

    /**
     * Returns true if {@code s} represents a non-zero unsigned integer
     * e.g. 1, 2, 3, ..., {@code Integer.MAX_VALUE} <br>
     * Will return false for any other non-null string input
     * e.g. empty string, "-1", "0", "+1", and " 2 " (unstripped), "3 0" (contains whitespace), "1 a" (contains letters)
     * @throws NullPointerException if {@code s} is null.
     */
    public static boolean isNonZeroUnsignedInteger(String s) {
        requireNonNull(s);

        try {
            int value = Integer.parseInt(s);
            return value > 0 && !s.startsWith("+"); // "+1" is successfully parsed by Integer#parseInt(String)
        } catch (NumberFormatException nfe) {
            return false;
        }
    }
}
