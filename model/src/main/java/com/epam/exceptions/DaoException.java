package com.epam.exceptions;


public class DaoException extends Exception {

    public DaoException() {
    }

    public DaoException(String messageCode) {
        super(messageCode);
    }

    public DaoException(String messageCode, Throwable cause) {
        super(messageCode, cause);
    }

    public DaoException(Throwable cause) {
        super(cause);
    }

}
