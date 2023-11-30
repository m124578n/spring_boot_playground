package com.example.todolist.controller.dto;

import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;

public class Response {
    public String message;
    public ArrayList<String> error;

    public Response() {
        this.message = "ok";
        this.error = new ArrayList<>();
    }

    public Response Error() {
        this.message = "error";
        return this;
    }

    public Response ErrorMessage(List<ObjectError> errs) {
        errs.forEach(e -> this.error.add(e.getDefaultMessage()));
        return this;
    }

    public Response ErrorMessage(Throwable e) {
        this.error.add(e.getMessage());
        return this;
    }
}
