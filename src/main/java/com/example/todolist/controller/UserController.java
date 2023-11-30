package com.example.todolist.controller;

import com.example.todolist.controller.dto.Response;
import com.example.todolist.controller.dto.UserDto;
import com.example.todolist.model.User;
import com.example.todolist.service.UserService;
import com.example.todolist.util.Hash;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    Map<String, String> message = new HashMap<>();

    @Autowired
    UserService userService;

    @PostMapping("/register")
    public Response UserRegister(@Valid @RequestBody UserDto data, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new Response().Error().ErrorMessage(bindingResult.getAllErrors());
        }

        try {
            User user = new User();
            user.setName(data.name);
            user.setAccount(data.account);

            String pwdHash = Hash.generateHash(data.account, data.password);
            user.setPwdHash(pwdHash);

            userService.UserRegister(user);
        } catch (Exception e) {
            return new Response().Error().ErrorMessage(e);
        }

        return new Response();
    }
}
