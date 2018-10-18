package com.iquestion.pojo.pojogroup;

import com.iquestion.pojo.User;

public class UserInfo {

    private User user;

    private int commentCount;

    private int followerCount;

    private int followeeCount;

    private boolean followed;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public int getFollowerCount() {
        return followerCount;
    }

    public void setFollowerCount(int followerCount) {
        this.followerCount = followerCount;
    }

    public int getFolloweeCount() {
        return followeeCount;
    }

    public void setFolloweeCount(int followeeCount) {
        this.followeeCount = followeeCount;
    }

    public boolean isFollowed() {
        return followed;
    }

    public void setFollowed(boolean followed) {
        this.followed = followed;
    }
}
