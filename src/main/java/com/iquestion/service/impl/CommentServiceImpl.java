package com.iquestion.service.impl;

import com.iquestion.common.Constant;
import com.iquestion.mapper.CommentMapper;
import com.iquestion.pojo.Comment;
import com.iquestion.pojo.CommentExample;
import com.iquestion.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

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
    public void delete(Integer commentId) {
        commentMapper.deleteByPrimaryKey(commentId);
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
    public List<Comment> queryByEntity(Integer entityId, Integer entityType) {
        CommentExample commentExample = new CommentExample();
        CommentExample.Criteria criteria = commentExample.createCriteria();

        criteria.andEntityIdEqualTo(entityId);
        criteria.andEntityTypeEqualTo(entityType);

        return commentMapper.selectByExampleWithBLOBs(commentExample);
    }

    public int getUserCommentCount(int userId){

        CommentExample commentExample = new CommentExample();
        CommentExample.Criteria criteria = commentExample.createCriteria();

        criteria.andUserIdEqualTo(userId);
        return (int) commentMapper.countByExample(commentExample);
    }
}
