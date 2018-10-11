package com.iquestion.pojo;

import org.springframework.stereotype.Component;

@Component
public class HostHolder {

    private static ThreadLocal<User> users = new ThreadLocal();

    public static User getUser() {
      //  System.out.println("嚯嚯嚯当前用户是" + users.get().getName());
        return users.get();
    }

    public static void setUser(User user) {
        users.set(user);
    }

    public static void clear(){
        System.out.println("嚯嚯嚯在清理哦");
        users.remove();
        if(users.get() == null){
            System.out.println("确实删除成功了");
        }else {
            System.out.println("删除个蛋");
        }
    }
}
