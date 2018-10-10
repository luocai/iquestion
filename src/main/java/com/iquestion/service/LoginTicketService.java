package com.iquestion.service;

import com.iquestion.pojo.LoginTicket;

public interface LoginTicketService {

    void add(LoginTicket loginTicket);

    void delete(LoginTicket loginTicket);

    void update(LoginTicket loginTicket);

    LoginTicket queryById(Integer id);


}
