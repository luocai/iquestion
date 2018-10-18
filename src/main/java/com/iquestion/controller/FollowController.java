package com.iquestion.controller;

import com.iquestion.async.EventModel;
import com.iquestion.async.EventProducer;
import com.iquestion.async.EventType;
import com.iquestion.common.Constant;
import com.iquestion.common.Result;
import com.iquestion.pojo.HostHolder;
import com.iquestion.pojo.Question;
import com.iquestion.pojo.User;
import com.iquestion.pojo.pojogroup.UserInfo;
import com.iquestion.service.CommentService;
import com.iquestion.service.FollowService;
import com.iquestion.service.QuestionService;
import com.iquestion.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class FollowController {

    @Autowired
    private FollowService followService;

    @Autowired
    private EventProducer eventProducer;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    @RequestMapping("/followUser")
    @ResponseBody
    public Result follow(@RequestParam("userId") Integer userId){

        if(HostHolder.getUser() == null){
            return new Result(Constant.NEED_LOGIN,"未登录");
        }

        boolean res = followService.follow(HostHolder.getUser().getId(), Constant.ENTITY_USER,userId);

        if(res == false){
            return new Result(Constant.RESULT_CODE_SERVER_ERROR,"服务器异常");
        }

        //
        EventModel eventModel = new EventModel();
        eventModel.setActorId(HostHolder.getUser().getId());
        eventModel.setType(EventType.FOLLOW);
        eventModel.setEntityType(Constant.ENTITY_USER);
        eventModel.setEntityId(userId);
        eventModel.setEntityOwnerId(userId);
        eventProducer.fireEvent(eventModel);
        // 返回当前用户关注数量
        return new Result(Constant.RESULT_CODE_SUCCESS,"",followService.getFolloweeCount(HostHolder.getUser().getId(),Constant.ENTITY_USER));

    }

    @RequestMapping("/unfollowUser")
    @ResponseBody
    public Result unfollow(@RequestParam("userId") Integer userId){

        if(HostHolder.getUser() == null){
            return new Result(Constant.NEED_LOGIN,"未登录");
        }

        boolean res = followService.unfollow(HostHolder.getUser().getId(),Constant.ENTITY_USER,userId);

        if(res == false){
            return new Result(Constant.RESULT_CODE_SERVER_ERROR,"服务器异常");
        }

        return new Result(Constant.RESULT_CODE_SUCCESS,"",followService.getFolloweeCount(HostHolder.getUser().getId(),Constant.ENTITY_USER));
    }

    @RequestMapping(path = {"/followQuestion"}, method = {RequestMethod.POST})
    @ResponseBody
    public Result followQuestion(@RequestParam("questionId") int questionId) {
        if (HostHolder.getUser() == null) {
            return new Result(Constant.NEED_LOGIN,"未登录");
        }

        Question q = questionService.queryById(questionId);
        if (q == null) {
            return new Result(Constant.RESULT_CODE_SERVER_ERROR,"该问题不存在");
        }

        boolean res = followService.follow(HostHolder.getUser().getId(), Constant.ENTITY_QUESTION, questionId);

        if (res == false){
            return new Result(Constant.RESULT_CODE_SERVER_ERROR,"内部错误");
        }

        eventProducer.fireEvent(new EventModel(EventType.FOLLOW)
                .setActorId(HostHolder.getUser().getId()).setEntityId(questionId)
                .setEntityType(Constant.ENTITY_QUESTION).setEntityOwnerId(q.getUserId()));

        Map<String, Object> info = new HashMap<>();
        info.put("headUrl", HostHolder.getUser().getHeadUrl());
        info.put("name", HostHolder.getUser().getName());
        info.put("id", HostHolder.getUser().getId());
        info.put("count", followService.getFollowerCount(Constant.ENTITY_QUESTION, questionId));
        return new Result(Constant.RESULT_CODE_SUCCESS,info);
    }

    @RequestMapping(path = {"/unfollowQuestion"}, method = {RequestMethod.POST})
    @ResponseBody
    public Result unfollowQuestion(@RequestParam("questionId") int questionId) {
        if (HostHolder.getUser() == null) {
            return new Result(Constant.NEED_LOGIN,"未登录");
        }

        Question q = questionService.queryById(questionId);
        if (q == null) {
            return new Result(Constant.RESULT_CODE_SERVER_ERROR,"该问题不存在");
        }

        boolean ret = followService.unfollow(HostHolder.getUser().getId(), Constant.ENTITY_QUESTION, questionId);

        eventProducer.fireEvent(new EventModel(EventType.UNFOLLOW)
                .setActorId(HostHolder.getUser().getId()).setEntityId(questionId)
                .setEntityType(Constant.ENTITY_QUESTION).setEntityOwnerId(q.getUserId()));

        Map<String, Object> info = new HashMap<>();
        info.put("id", HostHolder.getUser().getId());
        info.put("count", followService.getFollowerCount(Constant.ENTITY_QUESTION, questionId));
        return new Result(Constant.RESULT_CODE_SUCCESS,info);
    }

    @RequestMapping(path = {"/user/{uid}/followers"}, method = {RequestMethod.GET})
    public String followers(Model model, @PathVariable("uid") int userId) {
        List<Integer> followerIds = followService.getFollowers(Constant.ENTITY_USER, userId, 0, 10);
        if (HostHolder.getUser() != null) {
            model.addAttribute("followers", getUsersInfo(HostHolder.getUser().getId(), followerIds));
        } else {
            model.addAttribute("followers", getUsersInfo(0, followerIds));
        }
        model.addAttribute("followerCount", followService.getFollowerCount(Constant.ENTITY_USER, userId));
        model.addAttribute("curUser", userService.queryById(userId));
        return "followers";
    }

    @RequestMapping(path = {"/user/{uid}/followees"}, method = {RequestMethod.GET})
    public String followees(Model model, @PathVariable("uid") int userId) {
        List<Integer> followeeIds = followService.getFollowees(userId, Constant.ENTITY_USER, 0, 10);

        if (HostHolder.getUser() != null) {
            model.addAttribute("followees", getUsersInfo(HostHolder.getUser().getId(), followeeIds));
        } else {
            model.addAttribute("followees", getUsersInfo(0, followeeIds));
        }
        model.addAttribute("followeeCount", followService.getFolloweeCount(userId, Constant.ENTITY_USER));
        model.addAttribute("curUser", userService.queryById(userId));
        return "followees";
    }

    private List<UserInfo> getUsersInfo(int localUserId, List<Integer> userIds) {
        List<UserInfo> userInfos = new ArrayList<>();
        for (Integer uid : userIds) {
            User user = userService.queryById(uid);
            if (user == null) {
                continue;
            }
            UserInfo userInfo = new UserInfo();
            userInfo.setUser(user);
            userInfo.setCommentCount(commentService.getUserCommentCount(uid));
            userInfo.setFollowerCount((int) followService.getFollowerCount(Constant.ENTITY_USER, uid));
            userInfo.setFolloweeCount((int) followService.getFolloweeCount(uid, Constant.ENTITY_USER));
            if (localUserId != 0) {
                userInfo.setFollowed(followService.isfollower(localUserId, Constant.ENTITY_USER, uid));
            } else {
                userInfo.setFollowed(false);
            }
            userInfos.add(userInfo);
        }
        return userInfos;
    }



}
