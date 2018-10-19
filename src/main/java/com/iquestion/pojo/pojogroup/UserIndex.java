package com.iquestion.pojo.pojogroup;

import com.iquestion.pojo.Question;
import com.iquestion.pojo.User;

public class UserIndex {

    private Question question;

    private int followCount;

    private User user;

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public int getFollowCount() {
        return followCount;
    }

    public void setFollowCount(int followCount) {
        this.followCount = followCount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
