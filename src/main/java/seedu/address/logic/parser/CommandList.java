package seedu.address.logic.parser;

import java.util.ArrayList;
import java.util.List;

public class CommandList {
    private static final List<String> COMMANDLIST = new ArrayList<String>();
    private static int currentCommand = 0;

    public static void addCommand(String args) {
        COMMANDLIST.add(args);
        currentCommand = COMMANDLIST.size();
    }

    public static String getPrevCommand() {
        if (currentCommand > 0) {
            currentCommand--;
        }
        return COMMANDLIST.get(currentCommand);
    }

    public static String getNextCommand() {
        if (currentCommand < COMMANDLIST.size() - 1) {
            currentCommand++;
        }
        return COMMANDLIST.get(currentCommand);
    }
}