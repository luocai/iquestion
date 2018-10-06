package com.iquestion.controller;

import com.iquestion.common.Constant;
import com.iquestion.common.Result;
import com.iquestion.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String login(){

        return "login";

    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String login(String username, String password){

        return "login";

    }

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public String register(Model model,
                           @RequestParam("username") String username,
                           @RequestParam("password") String password){
System.out.println("进来登录了啊啦理发 啊");
        Result result = userService.register(username,password);

        if(result.getresultCode().equals(Constant.RESULT_CODE_SERVER_ERROR)){

            model.addAttribute("result",result);
            return "relogin";

        }

        return "login";

    }
}
