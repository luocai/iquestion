package com.iquestion.service.impl;

import com.iquestion.common.Constant;
import com.iquestion.common.Result;
import com.iquestion.mapper.LoginTicketMapper;
import com.iquestion.mapper.UserMapper;
import com.iquestion.pojo.LoginTicket;
import com.iquestion.pojo.User;
import com.iquestion.pojo.UserExample;
import com.iquestion.service.UserService;
import com.iquestion.utils.Md5Util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.thymeleaf.spring5.context.SpringContextUtils;

import java.util.*;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private LoginTicketMapper loginTicketMapper;

    @Override
    public void add(User user) {
        userMapper.insert(user);
    }

    @Override
    public void delete(User user) {
        userMapper.deleteByPrimaryKey(user.getId());
    }

    @Override
    public void update(User user) {
        userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public User queryById(Integer userId) {
        User user = userMapper.selectByPrimaryKey(userId);
        return user;
    }

    @Override
    public User queryByName(String userName) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();

        criteria.andNameEqualTo(userName);
        List<User> users = userMapper.selectByExample(userExample);

        if(users.isEmpty()){
            return null;
        }else {
            return users.get(0);
        }
    }

    @Override
    public Result register(String name, String password) {


        if(StringUtils.isEmpty(name.trim())){
            return new Result(Constant.RESULT_CODE_SERVER_ERROR,"用户名不能为空");
        }else if(StringUtils.isEmpty(password.trim())){
            return new Result(Constant.RESULT_CODE_SERVER_ERROR,"密码不能为空哦");
        }

        User user = userMapper.selectByName(name);
        if(user != null){
            return new Result(Constant.RESULT_CODE_SERVER_ERROR,"该用户名已经存在");
        }

        user = new User();
        user.setName(name);
        user.setHeadUrl("http://img4.duitang.com/uploads/item/201411/09/20141109142633_ncKBY.thumb.700_0.jpeg");
        user.setSalt(UUID.randomUUID().toString().substring(0,5));
        user.setPassword(Md5Util.MD5(password+user.getSalt()));

        userMapper.insert(user);

        return new Result(Constant.RESULT_CODE_SUCCESS,"注册成功");

    }

    @Override
    public Result login(String name, String password) {

        User user = userMapper.selectByName(name);

        if(StringUtils.isEmpty(name.trim())){
            return new Result(Constant.RESULT_CODE_SERVER_ERROR,"用户名不能为空");
        }

        if(StringUtils.isEmpty(password.trim())){
            return new Result(Constant.RESULT_CODE_SERVER_ERROR,"密码不能为空");
        }
System.out.println("这一步已经完成");
        if(user == null){
            return new Result(Constant.RESULT_CODE_SERVER_ERROR,"用户名不存在");
        }



        if(!user.getPassword().equals(Md5Util.MD5(password + user.getSalt()))){
            return new Result(Constant.RESULT_CODE_SERVER_ERROR,"密码错误");
        }else{
            Map<String,Object> map = new HashMap<>();
            String ticket = addLoginTicket(user.getId());
            map.put("ticket",ticket);
            map.put("userId",user.getId());
            return new Result(Constant.RESULT_CODE_SUCCESS,map);
        }


    }

    @Override
    public void logout(String ticket) {
        LoginTicket loginTicket = loginTicketMapper.selectByTicket(ticket);
        loginTicket.setStatus(1);
        loginTicketMapper.updateByPrimaryKeySelective(loginTicket);
    }

    private String addLoginTicket(Integer userId){

        LoginTicket ticket = new LoginTicket();
        ticket.setUserId(userId);
        Date date = new Date();
        date.setTime(date.getTime() + 1000*360*24);
        ticket.setExpired(date);
        ticket.setStatus(1);
        ticket.setTicket(UUID.randomUUID().toString().replaceAll("-",""));
        loginTicketMapper.insert(ticket);
        return ticket.getTicket();

    }
}
