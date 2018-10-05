package com.iquestion.common;

import java.util.HashMap;
import java.util.Map;

public class ViewObject {

    Map<String , Object> map = new HashMap<>();

    public void setData(String key, Object object){
        map.put(key,object);
    }

    public Object getData(String key){
        return map.get(key);
    }
}
