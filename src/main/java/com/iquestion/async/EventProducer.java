package com.iquestion.async;

import com.alibaba.fastjson.JSONObject;
import com.iquestion.common.Constant;
import com.iquestion.utils.JedisAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class EventProducer {

    @Autowired
    private JedisAdapter jedisAdapter;

    public boolean fireEvent(EventModel eventModel){

        try {
            String json = JSONObject.toJSONString(eventModel);
            jedisAdapter.lpush(Constant.BIZ_EVENTQUEUE,json);

            return true;
        }catch (Exception e){
            return false;
        }

    }
}
