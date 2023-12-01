package com.example.todolist.controller.dto;

import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Response {
    public String message;
    public Object data = new HashMap<>();
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

    public Response AddData(Map<String, String> data) {
        this.data = data;
        return this;
    }

    public Response AddData(Object obj) {
        this.data = obj;
        return this;
    }
}
