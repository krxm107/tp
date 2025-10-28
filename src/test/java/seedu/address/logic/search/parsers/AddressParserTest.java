package seedu.address.logic.search.parsers;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.search.SearchParserTestUtil.assertParseFailure;
import static seedu.address.logic.search.SearchParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.Collections;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.address.logic.search.predicates.AddressMatchesPredicate;
import seedu.address.model.person.Person;

public class AddressParserTest {

    private AddressParser<Person> parser = new AddressParser<Person>();

    @Test
    public void parse_validArgs_returnsCorrectPredicate() {
        // one keyword
        Predicate<Person> expectedPredicate =
                new AddressMatchesPredicate<>(Collections.singletonList("Jurong"));

        assertParseSuccess(parser, " Jurong ", expectedPredicate);

        // multiple keywords
        expectedPredicate = new AddressMatchesPredicate<>(Arrays.asList("Jurong", "Bedok"));
        assertParseSuccess(parser, " \n Jurong \n \t Bedok  \t", expectedPredicate);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // empty argument
        assertParseFailure(parser, "   ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchParser.MESSAGE_USAGE));
    }

}
