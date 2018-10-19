package com.iquestion.async;


import java.util.List;

public interface EventHandler {

    //具体的处理
    void doHander(EventModel eventModel);

    //找关联的事件
    List<EventType> getSupportEventTypes();
}
