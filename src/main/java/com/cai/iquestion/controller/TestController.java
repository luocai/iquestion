package com.cai.iquestion.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
public class TestController {

    @RequestMapping("/hello")
    @ResponseBody
    public String hello(){

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
        return "test";
    }
}
