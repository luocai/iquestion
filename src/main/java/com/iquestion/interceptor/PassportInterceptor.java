package com.iquestion.interceptor;

import com.iquestion.mapper.LoginTicketMapper;
import com.iquestion.mapper.UserMapper;
import com.iquestion.pojo.HostHolder;
import com.iquestion.pojo.LoginTicket;
import com.iquestion.pojo.User;
import com.iquestion.service.LoginTicketService;
import org.apache.catalina.Host;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Component
public class PassportInterceptor implements HandlerInterceptor {

    @Autowired
    private LoginTicketMapper loginTicketMapper;

    @Autowired
    private UserMapper userMapper;

    //该方法将在请求处理之前进行调用，只有该方法返回true，才会继续执行后续的Interceptor和Controller
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String ticket = null;
        if(request.getCookies() != null){
            for(Cookie cookie: request.getCookies()){
                if(cookie.getName().equals("ticket")) {
                    ticket = cookie.getValue();
                    System.out.println(ticket);
                    break;
                }
            }
        }
System.out.println("没想到把，我竟然能够执行哈哈哈哈哈或或");
        if(ticket != null){
            LoginTicket loginTicket = loginTicketMapper.selectByTicket(ticket);
            if (loginTicket == null || loginTicket.getExpired().before(new Date()) || loginTicket.getStatus()!= 0) {
                return true;
            }

            User user = userMapper.selectByPrimaryKey(loginTicket.getUserId());
            HostHolder.setUser(user);
        }

        return true;
    }

    //该方法将在请求处理之后，DispatcherServlet进行视图返回渲染之前进行调用，可以在这个方法中对Controller 处理之后的ModelAndView 对象进行操作。
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {

        if(modelAndView != null){

            System.out.println("现在执行的是post方法......");
            System.out.println(HostHolder.getUser());
            modelAndView.addObject("user",HostHolder.getUser());
        }
    }

    //该方法将在整个请求结束之后，也就是在DispatcherServlet 渲染了对应的视图之后执行。用于进行资源清理。
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {

        HostHolder.clear();
    }

}
