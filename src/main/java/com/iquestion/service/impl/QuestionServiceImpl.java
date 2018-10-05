package com.iquestion.service.impl;

import com.iquestion.mapper.QuestionMapper;
import com.iquestion.pojo.Question;
import com.iquestion.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService{

    @Autowired
    private QuestionMapper questionMapper;

    @Override
    public void add(Question question) {
        questionMapper.insert(question);
    }

    @Override
    public void delete(Question question) {
        questionMapper.deleteByPrimaryKey(question.getId());
    }

    @Override
    public void update(Question question) {
        questionMapper.updateByPrimaryKeySelective(question);
    }

    @Override
    public Question queryById(Integer id) {
        return questionMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Question> queryLatestQuestions(String userId, Integer offset, Integer limit) {
        return questionMapper.selectLatestQuestions(userId,offset,limit);
    }

    @Override
    public List<Question> queryAll() {
        return questionMapper.selectByExample(null);
    }
}
