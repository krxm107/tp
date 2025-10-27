package seedu.address.model.field;

import java.util.Set;

import javafx.collections.ObservableList;
import seedu.address.model.membership.Membership;
import seedu.address.model.tag.Tag;

/**
 * Group together methods for accessing common fields between <code>Person</code> and <code>Club</code>
 */
public interface Searchable {

    public Name getName();

    public Phone getPhone();

    public Email getEmail();

    public Address getAddress();

    public Set<Tag> getTags();

    public ObservableList<Membership> getMemberships();

}
