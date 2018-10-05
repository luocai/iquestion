package com.iquestion.service.impl;

import com.iquestion.mapper.CommentMapper;
import com.iquestion.pojo.Comment;
import com.iquestion.pojo.CommentExample;
import com.iquestion.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public void add(Comment comment) {
        commentMapper.insert(comment);
    }

    @Override
    public void delete(Comment comment) {
        commentMapper.deleteByPrimaryKey(comment.getId());
    }

    @Override
    public void update(Comment comment) {
        commentMapper.updateByPrimaryKeySelective(comment);
    }

    @Override
    public List<Comment> queryAll() {
        return commentMapper.selectByExample(null);
    }

    @Override
    public Comment queryById(Integer id) {
        return commentMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Comment> queryByUserId(Integer userId) {
        CommentExample commentExample = new CommentExample();
        CommentExample.Criteria criteria = commentExample.createCriteria();

        criteria.andUserIdEqualTo(userId);
        return commentMapper.selectByExample(commentExample);
    }
}
