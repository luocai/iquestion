package com.iquestion.controller;

import com.iquestion.pojo.User;
import com.iquestion.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
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
    public String hello(Model model){

        User user = new User();
        user.setId(1);
        user.setName("caicai");
        user.setPassword("...");
        user.setSalt(".l");
        user.setHeadUrl("helel");

        User user2 = new User();
        user2.setId(2);
        user2.setName("pingp");
        user2.setPassword("...");
        user2.setSalt(".l");
        user2.setHeadUrl("hddel");

        model.addAttribute("hero","求链接历程");

        List<String> xpp = new ArrayList<>();
        xpp.add("美丽");
        xpp.add("erhuo");
        xpp.add("嘿嘿嘿");

        Map<String , Object> map = new HashMap<>();
        map.put("user",user);
        map.put("name","qiuluoci");
        Map<String,Object> map1 = new HashMap<>();
        map1.put("user",user2);
        map1.put("name","xiopp");

        List<Object> haha = new ArrayList<>();
        haha.add(map);
        haha.add(map1);

        model.addAttribute("xpp",xpp);
        model.addAttribute("haha",haha);
        return "test";

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
