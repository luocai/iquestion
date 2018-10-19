package com.iquestion.controller;

import com.iquestion.common.Constant;
import com.iquestion.pojo.Question;
import com.iquestion.pojo.pojogroup.Questions;
import com.iquestion.service.FollowService;
import com.iquestion.service.QuestionService;
import com.iquestion.service.SearchService;
import com.iquestion.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nowcoder on 2016/7/24.
 */
@Controller
public class SearchController {
    private static final Logger logger = LoggerFactory.getLogger(SearchController.class);
    @Autowired
    SearchService searchService;

    @Autowired
    FollowService followService;

    @Autowired
    UserService userService;

    @Autowired
    QuestionService questionService;

    @RequestMapping(path = {"/search"}, method = {RequestMethod.GET})
    public String search(Model model, @RequestParam("q") String keyword,
                         @RequestParam(value = "offset", defaultValue = "0") int offset,
                         @RequestParam(value = "count", defaultValue = "10") int count) {
        try {
            List<Question> questionList = searchService.searchQuestion(keyword, offset, count,
                    "<em>", "</em>");
            List<Questions> questionsList = new ArrayList<>();
            for (Question question : questionList) {
                Question q = questionService.queryById(question.getId());
                Questions questions = new Questions();
                if (question.getContent() != null) {
                    q.setContent(question.getContent());
                }
                if (question.getTitle() != null) {
                    q.setTitle(question.getTitle());
                }
                questions.setQuestion(q);
                questions.setUser(userService.queryById(q.getUserId()));
                questions.setFollowCount((int) followService.getFollowerCount(Constant.ENTITY_QUESTION,question.getId()));

                questionsList.add(questions);
            }
            model.addAttribute("questionsList", questionsList);
            model.addAttribute("keyword", keyword);
        } catch (Exception e) {
            logger.error("搜索评论失败" + e.getMessage());
        }
        return "result";
    }
}
