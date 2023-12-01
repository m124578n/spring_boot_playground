package com.example.todolist.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;

/**
 * 待辦事項
 */
@Entity
@Table
public class Event extends BaseModel {

    /**
     * 使用者
     * <p>
     * 建立與User的關聯，關聯外鍵 user.id
     */
    @Getter
    @ManyToOne
    @JoinColumn(name = "userId")
    @JsonIgnore
    private User user;

    /**
     * 標題
     */
    @Getter
    @Column
    private String title;

    /**
     * 內容
     */
    @Getter
    @Column
    private String content;

    /**
     * 事件狀態
     *
     * @EventStatus
     */
    @Column
    private EventStatus status;

    public Event(String title, String content, User user) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.status = EventStatus.ACTION;
    }

    public Event() {

    }

    public EventStatus getEventStatus() {
        return this.status;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setStatusComplete() {
        this.status = EventStatus.COMPLETE;
    }

}