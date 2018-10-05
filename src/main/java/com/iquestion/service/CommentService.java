package com.iquestion.service;

import com.iquestion.pojo.Comment;

import java.util.List;

public interface CommentService {

    void add(Comment comment);

    void delete(Comment comment);

    void update(Comment comment);

    List<Comment> queryAll();

    Comment queryById(Integer id);

    List<Comment> queryByUserId(Integer userId);

}
