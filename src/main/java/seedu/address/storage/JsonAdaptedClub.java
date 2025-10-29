package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.club.Club;
import seedu.address.model.field.Address;
import seedu.address.model.field.Email;
import seedu.address.model.field.Name;
import seedu.address.model.field.Phone;
import seedu.address.model.field.validator.PhoneValidator;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Club}.
 */
class JsonAdaptedClub {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Club's %s field is missing!";

    private final String name;
    private final String phone;
    private final String email;
    private final String address;
    private final List<JsonAdaptedTag> tags = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedClub} with the given club details.
     */
    @JsonCreator
    public JsonAdaptedClub(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
                             @JsonProperty("email") String email, @JsonProperty("address") String address,
                             @JsonProperty("tags") List<JsonAdaptedTag> tags) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        if (tags != null) {
            this.tags.addAll(tags);
        }
    }

    /**
     * Converts a given {@code Club} into this class for Jackson use.
     */
    public JsonAdaptedClub(Club source) {
        name = source.getName().fullName;
        phone = source.getPhone() == null ? "" : source.getPhone().value;
        email = source.getEmail().value;
        this.address = source.getAddress() == null ? "" : source.getAddress().value;
        tags.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted club object into the model's {@code Club} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted club.
     */
    public Club toModelType() throws IllegalValueException {
        final List<Tag> clubTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tags) {
            clubTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        final Phone modelPhone;
        if (phone == null || phone.strip().isEmpty()) {
            modelPhone = new Phone(""); // optional phone
        } else if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(PhoneValidator.INVALID_PHONE_WARNING);
        } else {
            modelPhone = new Phone(phone);
        }

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }

        final Address modelAddress;
        if (address == null || address.strip().isEmpty()) {
            modelAddress = new Address(""); // optional address
        } else if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        } else {
            modelAddress = new Address(address);
        }

        final Set<Tag> modelTags = new HashSet<>(clubTags);
        return new Club(modelName, modelPhone, modelEmail, modelAddress, modelTags);
    }

}
