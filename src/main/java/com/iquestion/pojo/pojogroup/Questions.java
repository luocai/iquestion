package com.iquestion.pojo.pojogroup;

import com.iquestion.pojo.Question;
import com.iquestion.pojo.User;

public class Questions {

    private Question question;
    private User user;

    private int followCount;

    public int getFollowCount() {
        return followCount;
    }

    public void setFollowCount(int followCount) {
        this.followCount = followCount;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
