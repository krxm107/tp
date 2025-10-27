package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.logic.Messages.MESSAGE_NAME_EMAIL_COMPULSORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.field.Address;
import seedu.address.model.field.Email;
import seedu.address.model.field.Name;
import seedu.address.model.field.Phone;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing person in the address book.
 * <p>
 * NOTE: To satisfy the requirement, both Name and Email are treated as compulsory for edit.
 * The “invalid index” error takes precedence over the compulsory-field error.
 */
public class EditPersonCommand extends Command {

    public static final String COMMAND_WORD = "edit_person";
    public static final String COMMAND_SHORT = "editp";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the person identified "
            + "by the index number used in the displayed person list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_NAME + "John "
            + PREFIX_EMAIL + "john@example.com";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Person: %1$s";
    public static final String MESSAGE_NOT_EDITED = "The original and edited people are the same!";

    private final Index index;
    private final EditPersonDescriptor editPersonDescriptor;

    public EditPersonCommand(Index index, EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(index);
        requireNonNull(editPersonDescriptor);

        this.index = index;
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        // 1) Index check first (so invalid index beats all other errors)
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        // 2) Enforce compulsory Name + Email for edit (includes present-but-empty n/ or e/)
        boolean nameMissing = !editPersonDescriptor.getName().isPresent() || editPersonDescriptor.isNameEmptyFlag();
        boolean emailMissing = !editPersonDescriptor.getEmail().isPresent() || editPersonDescriptor.isEmailEmptyFlag();
        if (nameMissing || emailMissing) {
            throw new CommandException(MESSAGE_NAME_EMAIL_COMPULSORY);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = createEditedPerson(personToEdit, editPersonDescriptor);

        // Usual uniqueness / business rules (if any) can remain here.

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson)));
    }

    private static Person createEditedPerson(Person personToEdit, EditPersonDescriptor descriptor) {
        assert personToEdit != null;

        Name updatedName = descriptor.getName().orElse(personToEdit.getName());
        Phone updatedPhone = descriptor.getPhone().orElse(personToEdit.getPhone());
        Email updatedEmail = descriptor.getEmail().orElse(personToEdit.getEmail());
        Address updatedAddress = descriptor.getAddress().orElse(personToEdit.getAddress());
        Set<Tag> updatedTags = descriptor.getTags().orElse(personToEdit.getTags());

        // If you persist memberships by identity (index), nothing extra is needed here.
        return new Person(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditPersonCommand)) {
            return false;
        }

        // state check
        EditPersonCommand e = (EditPersonCommand) other;
        return index.equals(e.index)
                && editPersonDescriptor.equals(e.editPersonDescriptor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(index, editPersonDescriptor);
    }

    /**
     * Stores the details to edit the person with.
     * A field will be replaced only if it is non-empty in this descriptor.
     * Also holds flags for “present but empty” inputs (e.g. n/ or e/) so
     * {@link #execute(Model)} can throw the unified message after index validation.
     */
    public static class EditPersonDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private Address address;
        private Set<Tag> tags;

        // Flags to record present-but-empty prefixes
        private boolean nameEmptyFlag = false;
        private boolean emailEmptyFlag = false;

        public EditPersonDescriptor() {}

        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setAddress(toCopy.address);
            setTags(toCopy.tags);
            this.nameEmptyFlag = toCopy.nameEmptyFlag;
            this.emailEmptyFlag = toCopy.emailEmptyFlag;
        }

        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, email, address, tags);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        // Empty-value flags
        public void setNameEmptyFlag(boolean v) { this.nameEmptyFlag = v; }
        public void setEmailEmptyFlag(boolean v) { this.emailEmptyFlag = v; }
        public boolean isNameEmptyFlag() { return nameEmptyFlag; }
        public boolean isEmailEmptyFlag() { return emailEmptyFlag; }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }
            if (!(other instanceof EditPersonDescriptor)) {
                return false;
            }
            EditPersonDescriptor e = (EditPersonDescriptor) other;

            return getName().equals(e.getName())
                    && getPhone().equals(e.getPhone())
                    && getEmail().equals(e.getEmail())
                    && getAddress().equals(e.getAddress())
                    && getTags().equals(e.getTags())
                    && nameEmptyFlag == e.nameEmptyFlag
                    && emailEmptyFlag == e.emailEmptyFlag;
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, phone, email, address, tags, nameEmptyFlag, emailEmptyFlag);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("phone", phone)
                    .add("email", email)
                    .add("address", address)
                    .add("tags", tags)
                    .add("nameEmptyFlag", nameEmptyFlag)
                    .add("emailEmptyFlag", emailEmptyFlag)
                    .toString();
        }
    }
}

// Utility used above (import if you already have it elsewhere)
final class CollectionUtil {
    static boolean isAnyNonNull(Object... items) {
        for (Object o : items) {
            if (o != null) return true;
        }
        return false;
    }
}
