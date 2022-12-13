package com.epam.validator;

import com.epam.exceptions.ExceptionIncorrectParameterMessageCodes;
import com.epam.exceptions.IncorrectParameterException;
import lombok.experimental.UtilityClass;


@UtilityClass
public class IdentifiableValidator {
    private final int MIN_ID = 1;
    public void validateId(long id) throws IncorrectParameterException {
        if (id < MIN_ID) {
            throw new IncorrectParameterException(ExceptionIncorrectParameterMessageCodes.BAD_ID);
        }
    }
}
