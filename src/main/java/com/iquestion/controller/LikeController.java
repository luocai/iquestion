package com.iquestion.controller;

import com.iquestion.async.EventModel;
import com.iquestion.async.EventProducer;
import com.iquestion.async.EventType;
import com.iquestion.common.Constant;
import com.iquestion.common.Result;
import com.iquestion.pojo.Comment;
import com.iquestion.pojo.HostHolder;
import com.iquestion.service.CommentService;
import com.iquestion.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LikeController {

    @Autowired
    private LikeService likeService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private EventProducer eventProducer;

    @RequestMapping(path = {"/like"}, method = {RequestMethod.POST})
    @ResponseBody
    public Result like(@RequestParam("commentId") int commentId){

System.out.println("lllllllllllllllllll啪啪啪");
        if (HostHolder.getUser() == null){
            return new Result(Constant.NEED_LOGIN,"用户未登录");
        }

System.out.println("现在已经进了了哦哦哦哦哦哦哦哦");


        Comment comment = commentService.queryById(commentId);

        EventModel eventModel = new EventModel();
        eventModel.setEntityId(commentId);
        eventModel.setEntityType(Constant.COMMENT_TYPE);
        eventModel.setType(EventType.LIKE);
        eventModel.setActorId(comment.getUserId());
        eventModel.setExt("questionId",String.valueOf(comment.getEntityId()) );
        eventProducer.fireEvent(eventModel);

        System.out.println(comment);
        long likeCount = likeService.like(HostHolder.getUser().getId(),comment.getId(), Constant.COMMENT_TYPE);
        System.out.println(likeCount);
        return new Result(Constant.RESULT_CODE_SUCCESS,likeCount);
    }

    @RequestMapping(path = {"/dislike"}, method = {RequestMethod.POST})
    @ResponseBody
    public Result disLike(@RequestParam("commentId") int commentId){

        if (HostHolder.getUser() == null){
            return new Result(Constant.NEED_LOGIN,"用户未登录");
        }

        Comment comment = commentService.queryById(commentId);
        long likeCount = likeService.disLike(HostHolder.getUser().getId(),comment.getId(), Constant.COMMENT_TYPE);

        return new Result(Constant.RESULT_CODE_SUCCESS,likeCount);

    }
}
