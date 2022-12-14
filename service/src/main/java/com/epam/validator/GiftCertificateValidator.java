package com.epam.validator;

import com.epam.entity.GiftCertificate;
import com.epam.entity.Tag;
import com.epam.exceptions.ExceptionIncorrectParameterMessageCodes;
import com.epam.exceptions.IncorrectParameterException;
import lombok.experimental.UtilityClass;

import java.util.List;


@UtilityClass
public class GiftCertificateValidator {
    private final int MAX_LENGTH_NAME = 45;
    private final int MIN_LENGTH_NAME = 3;
    private final int MAX_LENGTH_DESCRIPTION = 300;
    private final double MIN_PRICE = 0.01;
    private final double MAX_PRICE = 999999.99;
    private final int MAX_DURATION = 366;
    private final int MIN_DURATION = 1;


    public void validate(GiftCertificate giftCertificate) throws IncorrectParameterException {
        validateName(giftCertificate.getName());
        validateDescription(giftCertificate.getDescription());
        validatePrice(giftCertificate.getPrice());
        validateDuration(giftCertificate.getDuration());

    }

    public void validateForUpdate(GiftCertificate giftCertificate) throws IncorrectParameterException {
        if (giftCertificate.getName() != null) {
            validateName(giftCertificate.getName());
        }
        if (giftCertificate.getDescription() != null) {
            validateDescription(giftCertificate.getDescription());
        }
        if (giftCertificate.getPrice() != 0) {
            validatePrice(giftCertificate.getPrice());
        }
        if (giftCertificate.getDuration() != 0) {
            validateDuration(giftCertificate.getDuration());
        }
        validateListOfTags(giftCertificate.getTags());
    }

    public void validateListOfTags(List<Tag> tags) throws IncorrectParameterException {
        if (tags == null) return;
        for (Tag tag : tags) {
            TagValidator.validate(tag);
        }
    }


    private void validateName(String name) throws IncorrectParameterException {
        if (name == null || name.length() < MIN_LENGTH_NAME || name.length() > MAX_LENGTH_NAME) {
            throw new IncorrectParameterException(ExceptionIncorrectParameterMessageCodes.BAD_GIFT_CERTIFICATE_NAME);
        }
    }

    private void validateDescription(String description) throws IncorrectParameterException {
        if (description == null || description.length() > MAX_LENGTH_DESCRIPTION) {
            throw new IncorrectParameterException(ExceptionIncorrectParameterMessageCodes.BAD_GIFT_CERTIFICATE_DESCRIPTION);
        }
    }

    private void validatePrice(double price) throws IncorrectParameterException {
        if (price > MAX_PRICE
                || price < MIN_PRICE) {
            throw new IncorrectParameterException(ExceptionIncorrectParameterMessageCodes.BAD_GIFT_CERTIFICATE_PRICE);
        }
    }

    private void validateDuration(int duration) throws IncorrectParameterException {
        if (duration < MIN_DURATION || duration > MAX_DURATION) {
            throw new IncorrectParameterException(ExceptionIncorrectParameterMessageCodes.BAD_GIFT_CERTIFICATE_DURATION);
        }
    }
}
