package com.example.todolist.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;

import java.io.Serializable;
import java.util.List;

@Entity
@Table
public class User extends BaseModel {

    /**
     * 使用者名稱
     */
    @Getter
    @Column
    private String name;

    /**
     * 帳號
     */
    @Getter
    @Column
    private String account;

    /**
     * 帳號+密碼 Hash
     */
    @Column
    private String pwdHash;

    @Getter
    @OneToMany(mappedBy = "user", cascade = { CascadeType.REMOVE })
    @JsonIgnore
    private List<Event> eventList;

    public void setName(String name) {
        this.name = name;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setPwdHash(String pwdHash) {
        this.pwdHash = pwdHash;
    }

    /**
     * 比較密碼Hash是否相同
     */
    public boolean comparePwdHash(String pwdHash) {
        // 引用類型之間的值比較要使用 equals
        return this.pwdHash.equals(pwdHash);
    }
}
