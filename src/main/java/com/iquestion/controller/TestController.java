package com.iquestion.controller;

import com.iquestion.pojo.User;
import com.iquestion.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class TestController {

//    @Autowired
//    UserService userService;

    @RequestMapping("/hello")
    @ResponseBody
    public String hello(){
//
////        User user = new User();
////        user.setId(1);
////        user.setName("caicai");
////        user.setPassword("...");
////        user.setSalt(".l");
////        user.setHeadUrl("helel");
////        userService.add(user);

        return "hello";

    }

    @RequestMapping("/profile/{groupId}/{userId}")
    @ResponseBody
    public String hello(@PathVariable("userId") int userId,
                        @PathVariable("groupId") String groupId){

        return String.format("....%s...%d",groupId,userId);

    }


    @RequestMapping(path={"/test"},method = {RequestMethod.GET})
    public String helloTest(Map<String,Object> map){

        map.put("name","luocai");
        map.put("wife","xpp");

        List<Object> goodList = new ArrayList<>();
        goodList.add("苹果");
        goodList.add("香蕉");
        goodList.add("芒果");

        map.put("goodList",goodList);

        return "for";
    }
}
