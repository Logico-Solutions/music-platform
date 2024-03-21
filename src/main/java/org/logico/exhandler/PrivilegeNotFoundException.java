package org.logico.exhandler;

public class PrivilegeNotFoundException extends RuntimeException {

    public PrivilegeNotFoundException() {
    }

    public PrivilegeNotFoundException(String message) {
        super(message);
    }

    public PrivilegeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public PrivilegeNotFoundException(Throwable cause) {
        super(cause);
    }
}
