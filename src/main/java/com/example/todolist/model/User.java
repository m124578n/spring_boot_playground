package com.example.todolist.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.io.Serializable;

@Getter
@Entity
@Table
public class User extends BaseModel  {

    public void setName(String name) {
        this.name = name;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setPwdHash(String pwdHash) {
        this.pwdHash = pwdHash;
    }

    @Column
    private String name;

    @Column
    private String account;

    @Column
    private String pwdHash;

    public boolean comparePwdHash(String pwdHash) {
        return this.pwdHash.equals(pwdHash);
    }

}

