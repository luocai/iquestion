package com.iquestion.service.impl;

import com.iquestion.mapper.FeedMapper;
import com.iquestion.pojo.Feed;
import com.iquestion.service.FeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedServiceImpl implements FeedService {

    @Autowired
    private FeedMapper feedMapper;

    @Override
    public List<Feed> getUserFeeds(int maxId, List<Integer> userIds, int count) {
        return feedMapper.selectUserFeeds(maxId,userIds,count);
    }

    @Override
    public boolean addFeed(Feed feed) {

        return feedMapper.insert(feed) > 0;

    }

    @Override
    public Feed getById(int id) {
        return feedMapper.selectByPrimaryKey(id);
    }
}
