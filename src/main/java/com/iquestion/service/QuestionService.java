package com.iquestion.service;

import com.iquestion.pojo.Question;

import java.util.List;

public interface QuestionService {

    void add(Question question);

    void delete(Question question);

    void update(Question question);

    Question queryById(Integer id);

    List<Question> queryLatestQuestions(Integer userId, Integer offset, Integer limit);

    List<Question> queryAll();
}
