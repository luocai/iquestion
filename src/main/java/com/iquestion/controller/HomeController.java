package com.iquestion.controller;


import com.iquestion.common.Constant;
import com.iquestion.pojo.HostHolder;
import com.iquestion.pojo.Question;
import com.iquestion.pojo.User;
import com.iquestion.pojo.pojogroup.Profile;
import com.iquestion.pojo.pojogroup.Questions;
import com.iquestion.pojo.pojogroup.UserIndex;
import com.iquestion.pojo.pojogroup.UserInfo;
import com.iquestion.service.CommentService;
import com.iquestion.service.FollowService;
import com.iquestion.service.QuestionService;
import com.iquestion.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nowcoder on 2016/7/15.
 */
@Controller
public class HomeController {
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    QuestionService questionService;

    @Autowired
    UserService userService;

    @Autowired
    FollowService followService;

    @Autowired
    CommentService commentService;



    private List<Questions> getQuestions(int userId, int offset, int limit) {
        List<Question> questionList = questionService.queryLatestQuestions(userId, offset, limit);
        List<Questions> questionsList = new ArrayList<>();
        for (Question question : questionList) {
            Questions questions = new Questions();
            questions.setQuestion(question);
            questions.setUser(userService.queryById(question.getUserId()));
            questions.setFollowCount((int) followService.getFollowerCount(Constant.ENTITY_QUESTION,question.getId()));

            questionsList.add(questions);
        }
        return questionsList;
    }



    @RequestMapping(path = {"/user/{userId}"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String Questions(Model model, @PathVariable("userId") int userId) {
        model.addAttribute("questionsList", getQuestions(userId, 0, 10));

        User user = userService.queryById(userId);
        Profile profile = new Profile();
        profile.setUser(user);
        profile.setCommentCount(commentService.getUserCommentCount(userId));
        profile.setFolloweeCount((int) followService.getFolloweeCount(userId,Constant.ENTITY_USER));
        profile.setFollowerCount((int) followService.getFollowerCount(Constant.ENTITY_USER,userId));

        if (HostHolder.getUser() != null) {
            profile.setFollow(followService.isfollower(HostHolder.getUser().getId(),Constant.ENTITY_USER,userId));
        } else {
            profile.setFollow(false);
        }
        model.addAttribute("profileUser", profile);
        return "profile";
    }
}
