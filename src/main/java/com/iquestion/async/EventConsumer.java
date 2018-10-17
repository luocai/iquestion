package com.iquestion.async;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.iquestion.common.Constant;
import com.iquestion.utils.JedisAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EventConsumer implements InitializingBean,ApplicationContextAware{

    private static final Logger logger = LoggerFactory.getLogger(EventConsumer.class);

    //用来映射每个事件对应的 处理事件
    private Map<EventType,List<EventHander>> config = new HashMap<>();

    //用来获取hander bean
    private ApplicationContext application ;

    @Autowired
    private JedisAdapter jedisAdapter;

    @Override
    public void afterPropertiesSet() throws Exception {
        //获取所有的hander事件
        Map<String,EventHander> beans = application.getBeansOfType(EventHander.class);

        for(Map.Entry<String,EventHander> entry:beans.entrySet()){

            //获取每个事件处理器对应的 事件类型
            List<EventType> eventTypes = entry.getValue().getSupportEventTypes();

            for (EventType eventType: eventTypes){

                if (!config.containsKey(eventType)) {
                    config.put(eventType,new ArrayList<EventHander>());
                }
                config.get(eventType).add(entry.getValue());
            }

        }
        // 首先获取所有的hander ，然后进行遍历，把每个hander 的触发事件（support event) 找出来， 对这个事件列表集合遍历 ，
        // 依次 放入config 容器中， 形成 键值对 （事件类型， 对应的事件集合）这么一个容器

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                while (true){
                    //.返回一个含有两个元素的列表,第一个元素是被弹出元素所属的 key ，第二个元素是被弹出元素的值。如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止。
                    List<String> events = jedisAdapter.brpop(0, Constant.BIZ_EVENTQUEUE);

                    for (String event: events){
                        //key : EIZ_EVENTQUEUE  过滤key
                        if(event.equals(Constant.BIZ_EVENTQUEUE)){
                            continue;
                        }

                        EventModel eventModel = JSON.parseObject(event,EventModel.class);

                        if(!config.containsKey(eventModel.getType())){
                            logger.error("不能识别的事件");
                            continue;
                        }

                        for(EventHander hander : config.get(eventModel.getType())){

                            hander.doHander(eventModel);

                        }
                    }
                }
            }
        });
        //开一个线程，负责检测 队列中是否有事件， 有的话就取出来，从 config 中找对应的hander， 找到后处理

        thread.start();

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.application = applicationContext;
    }
}
