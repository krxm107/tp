package seedu.address.logic.parser;

import java.util.ArrayList;
import java.util.List;

/**
 * Maintains a list of previously entered commands and provides
 * methods to retrieve previous or next commands for navigation.
 */
public class CommandList {
    private static final List<String> COMMAND_LIST = new ArrayList<>();
    private static int currentCommand = 0;

    /**
     * Adds a command to the command history and updates the current command index.
     *
     * @param args The command string to add.
     */
    public static void addCommand(String args) {
        COMMAND_LIST.add(args);
        currentCommand = COMMAND_LIST.size();
    }

    /**
     * Retrieves the previous command from the history.
     *
     * @return The previous command string.
     */
    public static String getPrevCommand() {
        if (currentCommand > 0) {
            currentCommand--;
        }
        return COMMAND_LIST.get(currentCommand);
    }

    /**
     * Retrieves the next command from the history.
     *
     * @return The next command string.
     */
    public static String getNextCommand() {
        if (currentCommand < COMMAND_LIST.size() - 1) {
            currentCommand++;
        }
        return COMMAND_LIST.get(currentCommand);
    }
}
