package com.epam.validator;

import lombok.experimental.UtilityClass;
import com.epam.entity.Tag;


@UtilityClass
public class TagValidator {
    private final int MAX_LENGTH_NAME = 20;
    private final int MIN_LENGTH_NAME = 3;

    public void validate(Tag tag) throws IncorrectParameterException {
        validateName(tag.getName());
    }

    public void validateName(String name) throws IncorrectParameterException {
        if (name.length() < MIN_LENGTH_NAME || name.length() > MAX_LENGTH_NAME) {
            throw new IncorrectParameterException(ExceptionIncorrectParameterMessageCodes.BAD_TAG_NAME);
        }
    }
}
