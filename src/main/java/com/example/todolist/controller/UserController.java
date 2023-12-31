package com.example.todolist.controller;

import com.example.todolist.controller.dto.Response;
import com.example.todolist.controller.dto.UserDto;
import com.example.todolist.controller.dto.UserLoginDto;
import com.example.todolist.controller.dto.UserUpdateDto;
import com.example.todolist.model.User;
import com.example.todolist.service.UserService;
import com.example.todolist.util.Hash;
import io.jsonwebtoken.lang.Strings;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    Map<String, String> message = new HashMap<>();

    @Autowired
    UserService userSvc;

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

            userSvc.UserRegister(user);
        } catch (Exception e) {
            return new Response().Error().ErrorMessage(e);
        }

        return new Response();
    }

    /**
     * 使用者登入
     */
    @PostMapping("/login")
    public Response UserLogin(@Valid @RequestBody UserLoginDto data, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return new Response().Error().ErrorMessage(bindingResult.getAllErrors());
        }

        try {

            // 使用者登入
            User user = userSvc.UserLogin(data.account, data.password);

            // 產生 Token
            String token = userSvc.GenerateToken(user);

            Map<String, String> res = new HashMap<>();
            res.put("token", token);

            return new Response().AddData(res);
        } catch (Exception e) {
            return new Response().Error().ErrorMessage(e);
        }

    }

    /**
     * 取得使用者資訊
     */
    @GetMapping("/info")
    public Response UserInfo() {

        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Long userId = (Long) auth.getPrincipal();

            User user = userSvc.GetUserInfoById(userId);

            return new Response().AddData(user);
        } catch (Exception e) {

            return new Response().Error().ErrorMessage(e);

        }

    }

    /**
     * 更新使用者名稱
     */
    @PutMapping("/info/name")
    public Response UpdateUserName(@RequestBody UserUpdateDto req) {
        // 更新name則name不可為空
        if (!Strings.hasText(req.name)) {
            return new Response().Error().ErrorMessage(new IllegalArgumentException("名稱不可為空"));
        }

        try {

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Long userId = (Long) auth.getPrincipal();

            userSvc.UpdateUserName(userId, req.name);

            return new Response();
        } catch (Exception e) {
            return new Response().Error().ErrorMessage(e);

        }

    }

    /**
     * 更新使用者名稱
     */
    @PutMapping("/info/password")
    public Response UpdateUserPassword(@RequestBody UserUpdateDto req) {
        // 更新password則password不可為空
        if (!Strings.hasText(req.password)) {
            return new Response().Error().ErrorMessage(new IllegalArgumentException("新密碼不可為空"));
        }

        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Long userId = (Long) auth.getPrincipal();

            userSvc.UpdateUserPassword(userId, req.password);

            return new Response();
        } catch (Exception e) {
            return new Response().Error().ErrorMessage(e);

        }

    }

    /**
     * 登出
     */
    @PostMapping("/logout")
    public Response Logout() {

        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Long userId = (Long) auth.getPrincipal();

            userSvc.Logout(userId);
            return new Response();
        } catch (Exception e) {
            return new Response().Error().ErrorMessage(e);

        }

    }
}
