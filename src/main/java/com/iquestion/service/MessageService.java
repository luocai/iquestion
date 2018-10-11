package com.iquestion.service;

import com.iquestion.pojo.Message;

import java.util.List;

public interface MessageService {

    void add(Message message);

    public List<Message> getConversationDetail(String conversationId, int offset, int limit);

    public List<Message> getConversationList(int userId, int offset, int limit) ;

    public int getConversationUnreadCount(int userId, String conversationId) ;

}
