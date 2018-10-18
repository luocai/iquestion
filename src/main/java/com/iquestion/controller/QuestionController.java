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

    @Autowired
    private FollowService followService;

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

        List<Comment>  commentList = commentService.queryByEntity(questionId,Constant.ENTITY_QUESTION);
        model.addAttribute("question",question);
        //封装了视图类
        List<Answers> answersList = new ArrayList<>() ;

        for (Comment comment: commentList){

            Answers answers = new Answers();
            User user = userService.queryById(comment.getUserId());

            answers.setComment(comment);
            answers.setLikeCount(likeService.getLikeCount(comment.getId(),Constant.ENTITY_COMMENT));
            answers.setUsername(user.getName());
            if(HostHolder.getUser() == null){
                answers.setLikeStatus(0);
            }else {
                answers.setLikeStatus(likeService.getLikeStatus(comment.getId(),Constant.ENTITY_COMMENT,HostHolder.getUser().getId()));
            }

System.out.println("hahhahahhhah哈哈哈发货单韩非韩非积分.........状态：" + answers.getLikeStatus());
            System.out.println(answers);
            answersList.add(answers);

        }

        List<User> followers = new ArrayList<>();
        List<Integer> users = followService.getFollowers(Constant.ENTITY_QUESTION,questionId,20);
        for(int userId  : users){

            User user = userService.queryById(userId);
            if(user != null){
                followers.add(user);
            }

        }

        model.addAttribute("follow",followService.isfollower(HostHolder.getUser().getId(),Constant.ENTITY_QUESTION,questionId));
        model.addAttribute("followers",followers);
        model.addAttribute("commentList",commentList);
        model.addAttribute("answersList",answersList);

        System.out.println("测试下内容");
        for(Comment comment: commentList){
            System.out.println(comment.getContent());
        }

        return "detail";
    }
}
