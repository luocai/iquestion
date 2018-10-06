package com.iquestion.service;

import com.iquestion.common.Result;
import com.iquestion.pojo.User;

public interface UserService {

    void add(User user);

    void delete(User user);

    void update(User user);

    User queryById(Integer userId);

    User queryByName(String userName);

    Result register(String name, String password);

    Result login(String name, String password);
}
