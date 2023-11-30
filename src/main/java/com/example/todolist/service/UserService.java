package com.example.todolist.service;

import com.example.todolist.exception.error.UserException;
import com.example.todolist.model.User;
import com.example.todolist.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepo repo;

    public void UserRegister(User user) throws Exception {
        try {
            User savedUser = repo.save(user);
        } catch (Exception e) {
            throw new UserException().CreateFail(e);
        }
    }
}
