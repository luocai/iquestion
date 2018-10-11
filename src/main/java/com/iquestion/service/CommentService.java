package com.iquestion.service;

import com.iquestion.pojo.Comment;

import java.util.List;

public interface CommentService {

    void add(Comment comment);

    void delete(Integer commentId);

    void update(Comment comment);

    List<Comment> queryAll();

    Comment queryById(Integer id);

    List<Comment> queryByEntity(Integer entityId, Integer entityType);

    public int getUserCommentCount(int userId);

}
