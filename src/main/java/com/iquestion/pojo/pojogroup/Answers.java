package com.iquestion.pojo.pojogroup;

import com.iquestion.pojo.Comment;

public class Answers {

    private Comment comment;

    private String username;

    private long likeCount;

    public int getLikeStatus() {
        return likeStatus;
    }

    public void setLikeStatus(int likeStatus) {
        this.likeStatus = likeStatus;
    }

    private int likeStatus;

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getLikeCount() {
        return likeCount;
    }

    @Override
    public String toString() {
        return "Answers{" +
                "comment=" + comment +
                ", username='" + username + '\'' +
                ", likeCount=" + likeCount +
                ", likeStatus=" + likeStatus +
                '}';
    }

    public void setLikeCount(Long likeCount) {
        this.likeCount = likeCount;
    }
}
