package seedu.address.logic.search;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class SearchUtilTest {

    @Test
    public void test_containsSubstring_returnsTrue() {
        // same word
        assertTrue(SearchUtil.containsSubstring("woodlands", "woodlands"));

        // same word, different case
        assertTrue(SearchUtil.containsSubstring("woodLANDS", "WOODlands"));

        // substring relation
        assertTrue(SearchUtil.containsSubstring("woodlands", "Woodlands Ave 5"));

        // empty keyword
        assertTrue(SearchUtil.containsSubstring("", "Woodlands Ave 5"));
    }

    @Test
    public void test_containsSubstring_returnsFalse() {
        // null keyword
        assertFalse(SearchUtil.containsSubstring(null, "Woodlands Ave 5"));

        // null field
        assertFalse(SearchUtil.containsSubstring("woodlands", null));

        // empty field
        assertFalse(SearchUtil.containsSubstring("woodlands", ""));

        // does not contain substring
        assertFalse(SearchUtil.containsSubstring("woodlands", "landswood"));
    }

}
