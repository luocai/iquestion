package com.iquestion.async.hander;

import com.iquestion.async.EventHandler;
import com.iquestion.async.EventModel;
import com.iquestion.async.EventType;
import com.iquestion.common.Constant;
import com.iquestion.pojo.Message;
import com.iquestion.pojo.User;
import com.iquestion.service.LikeService;
import com.iquestion.service.MessageService;
import com.iquestion.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
public class LikeHander implements EventHandler {

    @Autowired
    private LikeService likeService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @Override
    public void doHander(EventModel eventModel) {

        Message message = new Message();
        message.setFromId(Constant.SYSTEM_USERID);
        message.setToId(eventModel.getEntityOwnerId());
        message.setCreatedDate(new Date());

        User user = userService.queryById(eventModel.getActorId());
        message.setContent("用户" + user.getName() + "赞了你的评论,http://127.0.0.1:8080/question/" + eventModel.getExt("questionId"));

        messageService.add(message);
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.LIKE);
    }
}
