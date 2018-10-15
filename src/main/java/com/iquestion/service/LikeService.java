package com.iquestion.service;

import com.iquestion.utils.JedisAdapter;
import com.iquestion.utils.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeService {

    @Autowired
    private JedisAdapter jedisAdapter;


    //点赞，key: 生成 value:userId  用set存储，无序，不重复
    public long like(int userId,int entityId, int entityType){
        //点赞增加
        String likeKey = RedisKeyUtil.getLikeKey(entityId,entityType);
        jedisAdapter.sadd(likeKey,String.valueOf(userId));
        //反对则减少
        String disLikeKey = RedisKeyUtil.getDisLikeKey(entityId,entityType);
        return jedisAdapter.srem(disLikeKey,String.valueOf(userId));
    }


    //反对
    public long disLike(int userId, int entityId, int entityType){

        String disLikeKey = RedisKeyUtil.getDisLikeKey(entityId,entityType);
        jedisAdapter.sadd(disLikeKey, String.valueOf(userId));

        String likeKey = RedisKeyUtil.getLikeKey(entityId, entityType);
        return jedisAdapter.srem(likeKey,String.valueOf(userId));
    }

    //点赞总量
    public Long getLikeCount(int entityId, int entityType){

        String likeKey = RedisKeyUtil.getLikeKey(entityId,entityType);
        return jedisAdapter.scard(likeKey);

    }

    //点赞状态  1:已点赞 0没点也没反对 -1点了反对
    public int getLikeStatus(int entityId, int entityType,int userId){

        String likeKey = RedisKeyUtil.getLikeKey(entityId,entityType);
        if(jedisAdapter.sismember(likeKey, String.valueOf(userId))){
            return 1;
        }

        String disLikeKey = RedisKeyUtil.getDisLikeKey(entityId,entityType);
        if (jedisAdapter.sismember(disLikeKey,String.valueOf(userId))){
            return -1;
        }

        return 0;
    }



}
