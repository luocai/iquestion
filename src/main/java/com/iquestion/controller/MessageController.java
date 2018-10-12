package com.iquestion.controller;

import com.iquestion.common.Constant;
import com.iquestion.common.Result;
import com.iquestion.pojo.HostHolder;
import com.iquestion.pojo.Message;
import com.iquestion.pojo.User;
import com.iquestion.pojo.pojogroup.Messages;
import com.iquestion.service.MessageService;
import com.iquestion.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class MessageController {

    private Logger logger = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @RequestMapping(path = {"/msg"}, method = {RequestMethod.POST})
    @ResponseBody
    public Result addMessage(@RequestParam("toName") String toName,
                             @RequestParam("content") String content) {
        try {
            if (HostHolder.getUser() == null) {
                return new Result(Constant.NEED_LOGIN, "未登录");
            }
System.out.println("出来啦哈哈");
            User user = userService.queryByName(toName);
            if (user == null) {
                return new Result(Constant.RESULT_CODE_SERVER_ERROR,"用户不存在");
            }

            Message message = new Message();
            message.setCreatedDate(new Date());
            message.setFromId(HostHolder.getUser().getId());
            message.setToId(user.getId());
            message.setContent(content);
            messageService.add(message);
            return new Result(Constant.RESULT_CODE_SUCCESS);

        } catch (Exception e) {
            logger.error("发送消息失败" + e.getMessage());
            return new Result(Constant.RESULT_CODE_SERVER_ERROR, "发信失败");
        }
    }

    @RequestMapping(path = {"/msg/detail"}, method = {RequestMethod.GET})
    public String getConversationDetail(Model model, @RequestParam("conversationId") String conversationId) {
        try {
            List<Message> messageList = messageService.getConversationDetail(conversationId, 0, 10);
            List<Messages> messagesList = new ArrayList<Messages>();
            for (Message message : messageList) {
                Messages messages = new Messages();
                messages.setMessage(message);
                messages.setUser(userService.queryById(message.getFromId()));

                messagesList.add(messages);
            }
            model.addAttribute("messagesList", messagesList);
        } catch (Exception e) {
            logger.error("获取详情失败" + e.getMessage());
        }
        return "letterDetail";
    }

    @RequestMapping(path = {"/msg"}, method = {RequestMethod.GET})
    public String getConversationList(Model model) {
        if (HostHolder.getUser() == null) {
            return "redirect:/login";
        }
        int localUserId = HostHolder.getUser().getId();
        List<Message> conversationList = messageService.getConversationList(localUserId, 0, 10);
        List<Messages> conversations = new ArrayList<Messages>();
        for (Message message : conversationList) {
            Messages messages = new Messages();
            messages.setMessage(message);
            int targetId = message.getFromId() == localUserId ? message.getToId() : message.getFromId();
            messages.setUser(userService.queryById(targetId));
            messages.setUnRead(messageService.getConversationUnreadCount(localUserId,message.getConversationId()));

            conversations.add(messages);
        }
        model.addAttribute("conversations", conversations);
        return "letter";
    }
}
