package seedu.address.logic.search;

/**
 * Class containing utility methods for searching
 */
public class SearchUtil {

    /**
     * A method to check if a field contains a given keyword. Null will return false.
     */
    public static boolean containsSubstring(String keyword, String field) {
        if (keyword == null || field == null) {
            return false;
        } else {
            return field.toLowerCase().contains(keyword.toLowerCase());
        }
    }

}
