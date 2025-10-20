package seedu.address.commons.util;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

/**
 * A class for copying strings to the user's clipboard
 */
public class CopyUtil {

    public static void copyTextToClipboard(String text) {
        try {
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            StringSelection stringSelection = new StringSelection(text);
            clipboard.setContents(stringSelection, null);
        } catch (Exception e) {
            System.out.println("Error occurred, unable to copy to clipboard");
        }
    }

}
