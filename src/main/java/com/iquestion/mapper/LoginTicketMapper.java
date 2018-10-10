package com.iquestion.mapper;

import com.iquestion.pojo.LoginTicket;
import com.iquestion.pojo.LoginTicketExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LoginTicketMapper {
    long countByExample(LoginTicketExample example);

    int deleteByExample(LoginTicketExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(LoginTicket record);

    int insertSelective(LoginTicket record);

    List<LoginTicket> selectByExample(LoginTicketExample example);

    LoginTicket selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") LoginTicket record, @Param("example") LoginTicketExample example);

    int updateByExample(@Param("record") LoginTicket record, @Param("example") LoginTicketExample example);

    int updateByPrimaryKeySelective(LoginTicket record);

    int updateByPrimaryKey(LoginTicket record);

    LoginTicket selectByTicket(String ticket);
}