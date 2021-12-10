package com.Exceptions;

public class LoadLevelException extends Exception {
    public LoadLevelException() {
    }

    public LoadLevelException(String str) {
        super(str);

    }

    public LoadLevelException(String str, Throwable cause) {
        super(str, cause);

    }

    public LoadLevelException(Throwable cause) {
        super(cause);

    }
}
