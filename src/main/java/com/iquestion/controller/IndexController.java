package com.iquestion.controller;


import com.iquestion.common.Constant;
import com.iquestion.pojo.Question;
import com.iquestion.pojo.User;
import com.iquestion.pojo.pojogroup.Questions;
import com.iquestion.service.FollowService;
import com.iquestion.service.QuestionService;
import com.iquestion.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private UserService userService;

    @Autowired
    private FollowService followService;

    @RequestMapping(path={"/","/index"})
    public String index(Model model){



        model.addAttribute("questionsLine",getQuestionsLine(0,0,10));


        return "index";
    }


    @RequestMapping(path={"/user/{userId}"})
    public String userIndex(@PathVariable("userId") Integer userId,Model model){

        model.addAttribute("questionsLine",getQuestionsLine(userId,0,10));

        return "index";
    }

    private List<Questions> getQuestionsLine(Integer userId, Integer offset, Integer limit){

        List<Question> questionList = questionService.queryLatestQuestions(userId,offset,limit);
        List<Questions> questionsLine = new ArrayList<>();

        for(Question question : questionList){
            Questions questions = new Questions();
            User user = userService.queryById(question.getUserId());
            System.out.println(user.getName());
            questions.setQuestion(question);
            questions.setUser(user);
            questions.setFollowCount((int) followService.getFollowerCount(Constant.ENTITY_QUESTION,question.getId()));
            questionsLine.add(questions);
        }

        return  questionsLine;

    }
}
