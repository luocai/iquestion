package com.iquestion.service.impl;

import com.iquestion.mapper.MessageMapper;
import com.iquestion.pojo.Message;
import com.iquestion.pojo.MessageExample;
import com.iquestion.service.MessageService;
import com.iquestion.service.SensitiveService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageMapper messageMapper;




    @Override
    public void add(Message message) {

        messageMapper.insert(message);

    }

    @Override
    public List<Message> getConversationDetail(String conversationId, int offset, int limit) {

        return messageMapper.selectByConversationId(conversationId,offset,limit);
    }

    @Override
    public List<Message> getConversationList(int userId, int offset, int limit) {

        return messageMapper.selectByUserId(userId,offset,limit);
    }

    @Override
    public int getConversationUnreadCount(int userId, String conversationId) {
        return messageMapper.getConversationUnreadCount(userId,conversationId);
    }
}
