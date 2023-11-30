package com.example.todolist.repository;

import com.example.todolist.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends CrudRepository<User, Long> {
    public User findByAccountAndPwdHash(String account, String pwdHash);

}
