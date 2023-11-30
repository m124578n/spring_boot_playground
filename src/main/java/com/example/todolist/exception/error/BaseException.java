package com.example.todolist.exception.error;

public class BaseException extends RuntimeException {

    public BaseException() {
        super();
    }

    public BaseException(Throwable e) {
        super(e);
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
