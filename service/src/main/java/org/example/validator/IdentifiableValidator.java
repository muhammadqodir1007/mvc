package org.example.validator;

import lombok.experimental.UtilityClass;
import org.example.exceptions.IncorrectParameterException;

import static org.example.exceptions.ExceptionIncorrectParameterMessageCodes.BAD_ID;


@UtilityClass
public class IdentifiableValidator {
    private final int MIN_ID = 1;
    public void validateId(long id) throws IncorrectParameterException {
        if (id < MIN_ID) {
            throw new IncorrectParameterException(BAD_ID);
        }
    }
}
