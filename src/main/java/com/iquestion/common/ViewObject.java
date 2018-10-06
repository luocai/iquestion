package com.iquestion.common;

import java.util.HashMap;
import java.util.Map;

public class ViewObject {

    private Map<String , Object> data = new HashMap<>();

    public void set(String key, Object object){
        data.put(key,object);
    }

    public Object get(String key){
        return data.get(key);
    }
}
