package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.club.Club;
import seedu.address.model.field.Address;
import seedu.address.model.field.Email;
import seedu.address.model.field.Name;
import seedu.address.model.field.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Club objects.
 */
public final class ClubBuilder {

    public static final String DEFAULT_NAME = "Archery Club";
    public static final String DEFAULT_PHONE = "90123456";
    public static final String DEFAULT_EMAIL = "archery@gmail.com";
    public static final String DEFAULT_ADDRESS = "124, Jurong West Ave 2, #08-114";

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Set<Tag> tags;

    /**
     * Creates a {@code ClubBuilder} with the default details.
     */
    public ClubBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        tags = new HashSet<>();
    }

    /**
     * Initializes the ClubBuilder with the data of {@code clubToCopy}.
     */
    public ClubBuilder(Club clubToCopy) {
        name = clubToCopy.getName();
        phone = clubToCopy.getPhone();
        email = clubToCopy.getEmail();
        address = clubToCopy.getAddress();
        tags = new HashSet<>(clubToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Club} that we are building.
     */
    public ClubBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Club} that we are building.
     */
    public ClubBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Club} that we are building.
     */
    public ClubBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Club} that we are building.
     */
    public ClubBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Club} that we are building.
     */
    public ClubBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    public Club build() {
        return new Club(name, phone, email, address, tags);
    }

}
