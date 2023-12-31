package com.example.todolist.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UserDto {

    @NotNull
    @NotBlank(message = "名稱不可為空")
    public String name;

    @NotNull
    @NotBlank(message = "帳號不可為空")
    public String account;

    @NotNull
    @NotBlank(message = "密碼不可為空")
    public String password;
}
