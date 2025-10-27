package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_CLUB_DISPLAYED_INDEX;
import static seedu.address.logic.Messages.MESSAGE_NAME_EMAIL_COMPULSORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CLUBS;

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
import seedu.address.model.club.Club;
import seedu.address.model.field.Address;
import seedu.address.model.field.Email;
import seedu.address.model.field.Name;
import seedu.address.model.field.Phone;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing club in the address book.
 * Name and Email are treated as compulsory for edit.
 * Invalid index error takes precedence.
 */
public class EditClubCommand extends Command {

    public static final String COMMAND_WORD = "edit_club";
    public static final String COMMAND_SHORT = "editc";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the club identified "
            + "by the index number used in the displayed club list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_NAME + "Sports "
            + PREFIX_EMAIL + "sports@example.com";

    public static final String MESSAGE_EDIT_CLUB_SUCCESS = "Edited Club: %1$s";

    public static final String MESSAGE_NOT_EDITED = "The original and edited clubs are the same!";

    private final Index index;
    private final EditClubDescriptor editClubDescriptor;

    public EditClubCommand(Index index, EditClubDescriptor editClubDescriptor) {
        requireNonNull(index);
        requireNonNull(editClubDescriptor);

        this.index = index;
        this.editClubDescriptor = new EditClubDescriptor(editClubDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Club> lastShownList = model.getFilteredClubList();

        // 1) Index check first
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_CLUB_DISPLAYED_INDEX);
        }

        // 2) Enforce compulsory Name + Email
        boolean nameMissing = !editClubDescriptor.getName().isPresent() || editClubDescriptor.isNameEmptyFlag();
        boolean emailMissing = !editClubDescriptor.getEmail().isPresent() || editClubDescriptor.isEmailEmptyFlag();
        if (nameMissing || emailMissing) {
            throw new CommandException(MESSAGE_NAME_EMAIL_COMPULSORY);
        }

        Club clubToEdit = lastShownList.get(index.getZeroBased());
        Club editedClub = createEditedClub(clubToEdit, editClubDescriptor);

        model.setClub(clubToEdit, editedClub);
        model.updateFilteredClubList(PREDICATE_SHOW_ALL_CLUBS);

        // Optional: warnings (if you had any), else just success:
        return new CommandResult(String.format(MESSAGE_EDIT_CLUB_SUCCESS, Messages.format(editedClub)));
    }

    private static Club createEditedClub(Club clubToEdit, EditClubDescriptor descriptor) {
        assert clubToEdit != null;

        Name updatedName = descriptor.getName().orElse(clubToEdit.getName());
        Phone updatedPhone = descriptor.getPhone().orElse(clubToEdit.getPhone());
        Email updatedEmail = descriptor.getEmail().orElse(clubToEdit.getEmail());
        Address updatedAddress = descriptor.getAddress().orElse(clubToEdit.getAddress());
        Set<Tag> updatedTags = descriptor.getTags().orElse(clubToEdit.getTags());

        return new Club(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof EditClubCommand)) {
            return false;
        }
        EditClubCommand e = (EditClubCommand) other;
        return index.equals(e.index)
                && editClubDescriptor.equals(e.editClubDescriptor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(index, editClubDescriptor);
    }

    /**
     * Descriptor for editing a club.
     */
    public static class EditClubDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private Address address;
        private Set<Tag> tags;

        private boolean nameEmptyFlag = false;
        private boolean emailEmptyFlag = false;

        public EditClubDescriptor() {}

        public EditClubDescriptor(EditClubDescriptor toCopy) {
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

        public void setName(Name name) { this.name = name; }
        public Optional<Name> getName() { return Optional.ofNullable(name); }

        public void setPhone(Phone phone) { this.phone = phone; }
        public Optional<Phone> getPhone() { return Optional.ofNullable(phone); }

        public void setEmail(Email email) { this.email = email; }
        public Optional<Email> getEmail() { return Optional.ofNullable(email); }

        public void setAddress(Address address) { this.address = address; }
        public Optional<Address> getAddress() { return Optional.ofNullable(address); }

        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        public void setNameEmptyFlag(boolean v) { this.nameEmptyFlag = v; }
        public void setEmailEmptyFlag(boolean v) { this.emailEmptyFlag = v; }
        public boolean isNameEmptyFlag() { return nameEmptyFlag; }
        public boolean isEmailEmptyFlag() { return emailEmptyFlag; }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }
            if (!(other instanceof EditClubDescriptor)) {
                return false;
            }
            EditClubDescriptor e = (EditClubDescriptor) other;
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
