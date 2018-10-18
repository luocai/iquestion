package com.iquestion.service;

import com.iquestion.pojo.Feed;

import java.util.List;

public interface FeedService {

    public List<Feed> getUserFeeds(int maxId, List<Integer> userIds, int count);

    public boolean addFeed(Feed feed);

    public Feed getById(int id);
}
