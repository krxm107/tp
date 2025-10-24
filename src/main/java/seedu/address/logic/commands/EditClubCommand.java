package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
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
import seedu.address.commons.util.CollectionUtil;
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
 */
public class EditClubCommand extends Command {

    public static final String COMMAND_WORD = "edit_club";

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
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_EDIT_CLUB_SUCCESS = "Edited Club: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_CLUB = "This club already exists in the address book.";
    public static final String MESSAGE_DUPLICATE_CLUB_NAME = "A club with this name already exists.";
    public static final String MESSAGE_DUPLICATE_CLUB_EMAIL = "A club with this email already exists.";

    private final Index index;
    private final EditClubDescriptor editClubDescriptor;

    /**
     * @param index of the club in the filtered club list to edit
     * @param editClubDescriptor details to edit the club with
     */
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

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CLUB_DISPLAYED_INDEX);
        }

        Club clubToEdit = lastShownList.get(index.getZeroBased());
        Club editedClub = createEditedClub(clubToEdit, editClubDescriptor);

        // Existing broad duplicate check (keeps previous semantics)
        if (!clubToEdit.isSameClub(editedClub) && model.hasClub(editedClub)) {
            throw new CommandException(MESSAGE_DUPLICATE_CLUB);
        }

        /*
            Block collisions on name/email with *any other* club
            as name OR email are unique identifiers for a club.

            Set case-insensitive for both.
        */
        for (Club c : model.getAddressBook().getClubList()) {
            if (c.equals(clubToEdit)) {
                continue; // skip the club being edited
            }
            if (c.getName().fullName.equalsIgnoreCase(editedClub.getName().fullName)) {
                throw new CommandException(MESSAGE_DUPLICATE_CLUB_NAME);
            }
            if (c.getEmail().value.equalsIgnoreCase(editedClub.getEmail().value)) {
                throw new CommandException(MESSAGE_DUPLICATE_CLUB_EMAIL);
            }
        }

        model.setClub(clubToEdit, editedClub);
        model.updateFilteredClubList(PREDICATE_SHOW_ALL_CLUBS);
        return new CommandResult(String.format(MESSAGE_EDIT_CLUB_SUCCESS, Messages.format(editedClub)));
    }

    /**
     * Creates and returns a {@code Club} with the details of {@code clubToEdit}
     * edited with {@code editClubDescriptor}.
     */
    private static Club createEditedClub(Club clubToEdit, EditClubDescriptor editClubDescriptor) {
        assert clubToEdit != null;

        Name updatedName = editClubDescriptor.getName().orElse(clubToEdit.getName());
        Phone updatedPhone = editClubDescriptor.getPhone().orElse(clubToEdit.getPhone());
        Email updatedEmail = editClubDescriptor.getEmail().orElse(clubToEdit.getEmail());
        Address updatedAddress = editClubDescriptor.getAddress().orElse(clubToEdit.getAddress());
        Set<Tag> updatedTags = editClubDescriptor.getTags().orElse(clubToEdit.getTags());

        return new Club(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditClubCommand)) {
            return false;
        }

        EditClubCommand otherEditClubCommand = (EditClubCommand) other;
        return index.equals(otherEditClubCommand.index)
                && editClubDescriptor.equals(otherEditClubCommand.editClubDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editClubDescriptor", editClubDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the club with. Each non-empty field value will replace the
     * corresponding field value of the club.
     */
    public static class EditClubDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private Address address;
        private Set<Tag> tags;

        public EditClubDescriptor() {

        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditClubDescriptor(EditClubDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setAddress(toCopy.address);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
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

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditClubDescriptor)) {
                return false;
            }

            EditClubDescriptor otherEditClubDescriptor = (EditClubDescriptor) other;
            return Objects.equals(name, otherEditClubDescriptor.name)
                    && Objects.equals(phone, otherEditClubDescriptor.phone)
                    && Objects.equals(email, otherEditClubDescriptor.email)
                    && Objects.equals(address, otherEditClubDescriptor.address)
                    && Objects.equals(tags, otherEditClubDescriptor.tags);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("phone", phone)
                    .add("email", email)
                    .add("address", address)
                    .add("tags", tags)
                    .toString();
        }
    }
}
