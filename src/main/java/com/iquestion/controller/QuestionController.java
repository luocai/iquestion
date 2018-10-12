package com.iquestion.controller;

import com.iquestion.common.Constant;
import com.iquestion.common.Result;
import com.iquestion.pojo.Comment;
import com.iquestion.pojo.HostHolder;
import com.iquestion.pojo.Question;
import com.iquestion.pojo.User;
import com.iquestion.pojo.pojogroup.Answers;
import com.iquestion.service.*;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private SensitiveService sensitiveService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private LikeService likeService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/question",method = RequestMethod.POST)
    @ResponseBody
    public Result addQuestion(Model model,
                              @RequestParam("title") String title,
                              @RequestParam("content") String content){

        Question question = new Question();
System.out.println(title);
System.out.println(content);
        //格式转换，避免内容为js影响界面显示
        question.setTitle(HtmlUtils.htmlEscape(title));
        question.setContent(HtmlUtils.htmlEscape(content));

        //敏感词过滤
        question.setTitle(sensitiveService.filter(question.getTitle()));
        question.setContent(sensitiveService.filter(question.getContent()));

        question.setCreatedDate(new Date());
        question.setCommentCount(0);

        if(HostHolder.getUser() == null){
            //question.setUserId(Constant.ANONYMOUS_USERID);
            return new Result(Constant.NEED_LOGIN,"用户未登录");
        }else{
            question.setUserId(HostHolder.getUser().getId());
        }

        try {
            questionService.add(question);
        }catch (Exception e){
            return new Result(Constant.RESULT_CODE_SERVER_ERROR,"提问失败");
        }

        return new Result(Constant.RESULT_CODE_SUCCESS,"增加问题成功");
    }

    @RequestMapping(value = "/question/{questionId}", method = RequestMethod.GET)
    public String questionDetail(Model model,@PathVariable("questionId") Integer questionId){

        Question question = questionService.queryById(questionId);

        if(question == null){
            System.out.println("该问题不存在");
        }

        List<Comment>  commentList = commentService.queryByEntity(questionId,Constant.QUESTION_TYPE);
        model.addAttribute("question",question);
        //封装了视图类
        List<Answers> answersList = new ArrayList<>() ;

        for (Comment comment: commentList){

            Answers answers = new Answers();
            User user = userService.queryById(comment.getUserId());

            answers.setComment(comment);
            System.out.println("一共有这么多赞哦" + likeService.getLikeCount(comment.getEntityId(),Constant.QUESTION_TYPE));
            answers.setLikeCount(likeService.getLikeCount(comment.getEntityId(),Constant.QUESTION_TYPE));
            answers.setUsername(user.getName());
            answers.setLikeStatus(likeService.getLikeStatus(comment.getEntityId(),Constant.QUESTION_TYPE,user.getId()));

            answersList.add(answers);

        }

        model.addAttribute("commentList",commentList);
        model.addAttribute("answersList",answersList);

        System.out.println("测试下内容");
        for(Comment comment: commentList){
            System.out.println(comment.getContent());
        }

        return "detail";
    }
}
