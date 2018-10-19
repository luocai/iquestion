package com.iquestion.controller;

import com.iquestion.async.EventModel;
import com.iquestion.async.EventProducer;
import com.iquestion.async.EventType;
import com.iquestion.common.Constant;
import com.iquestion.common.Result;
import com.iquestion.pojo.Comment;
import com.iquestion.pojo.HostHolder;
import com.iquestion.service.CommentService;
import com.iquestion.service.SensitiveService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import java.util.Date;
import java.util.logging.Logger;


@Controller
public class CommentController {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(CommentController.class);

    @Autowired
    private CommentService commentService;

    @Autowired
    private SensitiveService sensitiveService;

    @Autowired
    private EventProducer eventProducer;


    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public String addComment(@RequestParam("content") String content,
                             @RequestParam("questionId") Integer questionId){
System.out.println("进来了评论区的一批哦");
        try {
            Comment comment = new Comment();

            /* 过滤敏感词 */
            comment.setContent(HtmlUtils.htmlEscape(content));
            comment.setContent(sensitiveService.filter(comment.getContent()));

            comment.setCreatedDate(new Date());
            comment.setEntityId(questionId);
            comment.setEntityType(Constant.ENTITY_QUESTION);
            if(HostHolder.getUser() != null)
                comment.setUserId(HostHolder.getUser().getId());
            commentService.add(comment);

            // 异步发送feed事件
            EventModel eventModel = new EventModel();
            eventModel.setActorId(comment.getUserId());
            eventModel.setType(EventType.COMMENT);
            eventModel.setEntityType(Constant.ENTITY_QUESTION);
            eventModel.setEntityId(questionId);
            eventProducer.fireEvent(eventModel);

        }catch (Exception e){
            logger.error("增加评论失败" + e.getMessage());
        }

        return "redirect:/question/" + questionId;
    }

    @RequestMapping(value = "/comment",method = RequestMethod.DELETE)
    @ResponseBody
    public Result deleteComment(@RequestParam("id") Integer id){

        try {
            commentService.delete(id);
            return new Result(Constant.RESULT_CODE_SUCCESS);
        }catch (Exception e){
            return new Result(Constant.RESULT_CODE_SERVER_ERROR,"删除失败");
        }
    }


}
