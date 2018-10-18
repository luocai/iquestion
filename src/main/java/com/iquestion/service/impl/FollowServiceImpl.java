package com.iquestion.service.impl;

import com.iquestion.service.FollowService;
import com.iquestion.utils.JedisAdapter;
import com.iquestion.utils.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class FollowServiceImpl implements FollowService{

    @Autowired
    private JedisAdapter jedisAdapter;


    //用户关注实体（问题，用户)
    @Override
    public boolean follow(int userId, int entityType, int entityId) {

        String followerKey = RedisKeyUtil.getFollowerKey(entityType,entityId);
        String followeeKey = RedisKeyUtil.getFolloweeKey(userId,entityType);

        Date date = new Date();
        Jedis jedis = jedisAdapter.getJedis();
        Transaction tx = jedis.multi();
        // 某实体增加一名 id 为userid的关注者
        tx.zadd(followerKey,date.getTime(),String.valueOf(userId));
        //某用户增加一个 entityID 为entityID 的关注对象
        tx.zadd(followeeKey,date.getTime(),String.valueOf(entityId));

        List<Object> res = jedisAdapter.exec(tx,jedis);

        return res.size() == 2 && (Long) res.get(0) > 0 && (Long) res.get(1) > 0;
    }

    //取消关注
    @Override
    public boolean unfollow(int userId, int entityType, int entityId) {

        String followerKey = RedisKeyUtil.getFollowerKey(entityType,entityId);
        String followeeKey = RedisKeyUtil.getFolloweeKey(userId,entityType);

        Jedis jedis = jedisAdapter.getJedis();
        Transaction tx = jedis.multi();
        //该实体减少userId 的关注者
        tx.zrem(followerKey,String.valueOf(userId));
        // 用户userId 减少一个关注对象
        tx.zrem(followeeKey,String.valueOf(entityId));

        List<Object> res = jedisAdapter.exec(tx,jedis);

        return res.size() == 2 && (Long) res.get(0) > 0 && (Long) res.get(1) > 0;
    }

    // 是否关注了某一实体
    @Override
    public boolean isfollower(int userId, int entityType, int entityId) {

        String followerKey = RedisKeyUtil.getFollowerKey(entityType,entityId);


        return jedisAdapter.zcard(followerKey) > 0;
    }

    @Override
    public List<Integer> getFollowers(int entityType, int entityId, int count) {
        String followerKey = RedisKeyUtil.getFollowerKey(entityType,entityId);

        return getIdsFromSet(jedisAdapter.zrevrange(followerKey,0,count));
    }

    @Override
    public List<Integer> getFollowers(int entityType, int entityId, int offset, int count) {

        String followerKey = RedisKeyUtil.getFollowerKey(entityType,entityId);

        return getIdsFromSet(jedisAdapter.zrevrange(followerKey, offset,count));
    }

    @Override
    public List<Integer> getFollowees(int userId, int entityType, int count) {
        String followeeKey = RedisKeyUtil.getFolloweeKey(userId, entityType);
        return getIdsFromSet(jedisAdapter.zrevrange(followeeKey, 0, count));
    }

    @Override
    public List<Integer> getFollowees(int userId, int entityType, int offset, int count) {
        String followeeKey = RedisKeyUtil.getFolloweeKey(userId, entityType);
        return getIdsFromSet(jedisAdapter.zrevrange(followeeKey, offset, count));
    }

    @Override
    public long getFollowerCount(int entityType, int entityId) {

        String followerKey = RedisKeyUtil.getFollowerKey(entityType,entityId);

        return jedisAdapter.zcard(followerKey);
    }

    @Override
    public long getFolloweeCount(int userId, int entityType) {
        String followeeKey = RedisKeyUtil.getFolloweeKey(userId, entityType);
        return jedisAdapter.zcard(followeeKey);
    }

    //把set 转为list
    private List<Integer> getIdsFromSet(Set<String> idset) {
        List<Integer> ids = new ArrayList<>();
        for (String str : idset) {
            ids.add(Integer.parseInt(str));
        }
        return ids;
    }


}
