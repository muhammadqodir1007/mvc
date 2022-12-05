package org.example.validator;

import lombok.experimental.UtilityClass;
import org.example.entity.Tag;
import org.example.exceptions.IncorrectParameterException;

import static org.example.exceptions.ExceptionIncorrectParameterMessageCodes.BAD_TAG_NAME;


@UtilityClass
public class TagValidator {
    private final int MAX_LENGTH_NAME = 20;
    private final int MIN_LENGTH_NAME = 3;

    public void validate(Tag tag) throws IncorrectParameterException {
        validateName(tag.getName());
    }

    public void validateName(String name) throws IncorrectParameterException {
        if (name.length() < MIN_LENGTH_NAME || name.length() > MAX_LENGTH_NAME) {
            throw new IncorrectParameterException(BAD_TAG_NAME);
        }
    }
}
