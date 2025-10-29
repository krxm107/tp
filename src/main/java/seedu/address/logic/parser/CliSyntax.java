package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_MEMBER = new Prefix("m/");
    public static final Prefix PREFIX_CLUB = new Prefix("c/");
    public static final Prefix PREFIX_DURATION = new Prefix("d/");
    public static final Prefix PREFIX_STATUS = new Prefix("s/");

    /* Field definitions */
    public static final String NAME = "n";
    public static final String PHONE = "p";
    public static final String EMAIL = "e";
    public static final String ADDRESS = "a";
    public static final String MEMBER = "m";
}
