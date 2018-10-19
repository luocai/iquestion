package com.iquestion.async.hander;

import com.iquestion.async.EventHandler;
import com.iquestion.async.EventModel;
import com.iquestion.async.EventType;
import com.iquestion.common.Constant;
import com.iquestion.pojo.Message;
import com.iquestion.pojo.User;
import com.iquestion.service.MessageService;
import com.iquestion.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
public class FollowHander implements EventHandler {

    @Autowired
    MessageService messageService;

    @Autowired
    UserService userService;

    @Override
    public void doHander(EventModel model) {
        Message message = new Message();
        message.setFromId(Constant.SYSTEM_USERID);
        message.setToId(model.getEntityOwnerId());
        message.setCreatedDate(new Date());
        User user = userService.queryById(model.getActorId());

        if (model.getEntityType() == Constant.ENTITY_QUESTION) {
            message.setContent("用户" + user.getName()
                    + "关注了你的问题,http://127.0.0.1:8080/question/" + model.getEntityId());
        } else if (model.getEntityType() == Constant.ENTITY_USER) {
            message.setContent("用户" + user.getName()
                    + "关注了你,http://127.0.0.1:8080/user/" + model.getActorId());
        }

        messageService.add(message);
    }


    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.FOLLOW);
    }
}
