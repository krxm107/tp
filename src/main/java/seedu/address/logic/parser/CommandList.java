package seedu.address.logic.parser;

import java.util.ArrayList;
import java.util.List;

/**
 * A utility class to maintain a history of user-entered commands,
 * allowing navigation through previous and next commands.
 */
public class CommandList {
    private static final List<String> COMMAND_LIST = new ArrayList<>();
    private static int currentCommand = 0;

    /**
     * Adds a new command to the command history.
     *
     * @param args The command string to add.
     */
    public static void addCommand(String args) {
        COMMAND_LIST.add(args);
        currentCommand = COMMAND_LIST.size();
    }

    /**
     * Returns the previous command in the history, if available.
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
     * Returns the next command in the history, if available.
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
