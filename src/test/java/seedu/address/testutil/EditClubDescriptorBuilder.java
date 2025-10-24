package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditClubCommand.EditClubDescriptor;
import seedu.address.model.club.Club;
import seedu.address.model.field.Address;
import seedu.address.model.field.Email;
import seedu.address.model.field.Name;
import seedu.address.model.field.Phone;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building EditClubDescriptor objects.
 */
public class EditClubDescriptorBuilder {

    private EditClubDescriptor descriptor;

    public EditClubDescriptorBuilder() {
        descriptor = new EditClubDescriptor();
    }

    public EditClubDescriptorBuilder(EditClubDescriptor descriptor) {
        this.descriptor = new EditClubDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditClubDescriptor} with fields containing {@code club}'s details
     */
    public EditClubDescriptorBuilder(Club club) {
        descriptor = new EditClubDescriptor();
        descriptor.setName(club.getName());
        descriptor.setPhone(club.getPhone());
        descriptor.setEmail(club.getEmail());
        descriptor.setAddress(club.getAddress());
        descriptor.setTags(club.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditClubDescriptor} that we are building.
     */
    public EditClubDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditClubDescriptor} that we are building.
     */
    public EditClubDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditClubDescriptor} that we are building.
     */
    public EditClubDescriptorBuilder withEmail(String email) {
        descriptor.setEmail(new Email(email));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditClubDescriptor} that we are building.
     */
    public EditClubDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new Address(address));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditClubDescriptor}
     * that we are building.
     */
    public EditClubDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditClubDescriptor build() {
        return descriptor;
    }
}
