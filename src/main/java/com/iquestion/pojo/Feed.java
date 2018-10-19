package com.iquestion.pojo;

import com.alibaba.fastjson.JSONObject;

import java.util.Date;

public class Feed {
    private Integer id;

    private Integer type;

    private Integer userid;

    private Date createddate;

    private String data;

    private JSONObject dataJSON = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Date getCreateddate() {
        return createddate;
    }

    public void setCreateddate(Date createddate) {
        this.createddate = createddate;
    }

    public String getData() {

        return data;
    }

    public void setData(String data) {
        this.dataJSON = JSONObject.parseObject(data);

        this.data = data == null ? null : data.trim();
    }
}