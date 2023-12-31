package com.example.todolist.exception.error;

public class EventException extends BaseException {

    public EventException() {
        super();
    }

    public EventException(String message, Throwable e) {
        super(message, e);
    }

    public EventException CreateFail(Throwable e) {
        return new EventException("建立待辦事項失敗", e);
    }

    public EventException UpdateFail(Throwable e) {
        return new EventException("更新待辦事項失敗", e);
    }

    public EventException RemoveFail(Throwable e) {
        return new EventException("刪除待辦事項失敗", e);
    }

    public EventException NotFoundEvent(Throwable e) {
        return new EventException("待辦事項不存在", e);
    }

}
