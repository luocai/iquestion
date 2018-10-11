package com.iquestion.mapper;

import com.iquestion.pojo.Message;
import com.iquestion.pojo.MessageExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MessageMapper {
    long countByExample(MessageExample example);

    int deleteByExample(MessageExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Message record);

    int insertSelective(Message record);

    List<Message> selectByExampleWithBLOBs(MessageExample example);

    List<Message> selectByExample(MessageExample example);

    Message selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Message record, @Param("example") MessageExample example);

    int updateByExampleWithBLOBs(@Param("record") Message record, @Param("example") MessageExample example);

    int updateByExample(@Param("record") Message record, @Param("example") MessageExample example);

    int updateByPrimaryKeySelective(Message record);

    int updateByPrimaryKeyWithBLOBs(Message record);

    int updateByPrimaryKey(Message record);

    List<com.iquestion.pojo.Message> selectByConversationId(@Param("conversationId") String conversationId,
                                                            @Param("offset") Integer offset,
                                                            @Param("limit") Integer limit);

    List<com.iquestion.pojo.Message> selectByUserId(@Param("userId") int userId,
                                                    @Param("offset") int offset,
                                                    @Param("limit") int limit);

    int getConversationUnreadCount(@Param("userId") int userId,
                                   @Param("conversationId") String conversationId);
}