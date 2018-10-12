package com.iquestion.pojo.pojogroup;

import com.iquestion.pojo.Message;
import com.iquestion.pojo.User;

public class Messages {

    private Message message;

    private User user;

    private int unRead;

    public int getUnRead() {
        return unRead;
    }

    public void setUnRead(int unRead) {
        this.unRead = unRead;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
