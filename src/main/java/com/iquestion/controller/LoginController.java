package com.iquestion.controller;

import com.iquestion.common.Constant;
import com.iquestion.common.Result;
import com.iquestion.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String login(Model model){

//        Result result = new Result(Constant.RESULT_CODE_SERVER_ERROR,"mimadf");
//        model.addAttribute("result",result);
//        model.addAttribute("hello","xpp");
        return "login";

    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String login(Model model,
                        @RequestParam("username") String username,
                        @RequestParam("password") String password,
                        @RequestParam(value = "next",required = false) String next,
                        @RequestParam(value = "rememberme",defaultValue = "false") Boolean rememberme,
                        HttpServletResponse response){
System.out.println("在德林路啦");
        Result result = userService.login(username,password);
System.out.println("已经出来拉埃");
        if(result.getResultCode() == Constant.RESULT_CODE_SERVER_ERROR){
System.out.println("真实失败");
            model.addAttribute("result",result);
            return "login";

        }
        Map<String,Object> map = (Map<String, Object>) result.getData();
        //这段代码搞啥子哦
        if(map.containsKey("ticket")){

            Cookie cookie = new Cookie("ticket",map.get("ticket").toString());
            cookie.setPath("/"); //同一个服务器内共享
            if (rememberme){
                cookie.setMaxAge(3600*24*5);
            }
            response.addCookie(cookie);

            if(StringUtils.isEmpty(next)){
                return "redirect:" + next;
            }

        }
        return "redirect:/index";

    }

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public String register(Model model,
                           @RequestParam("username") String username,
                           @RequestParam("password") String password){
System.out.println("进来登录了啊啦理发 啊");
        Result result = userService.register(username,password);

        if(result.getResultCode() == Constant.RESULT_CODE_SERVER_ERROR){

            model.addAttribute("result",result);
            return "redirect:/login";

        }

        return "login";

    }

    @RequestMapping("/logout")
    public String logout(@CookieValue("ticket") String ticket){

        userService.logout(ticket);
        return "redirect:/index";
    }
}
