package com.iquestion.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public interface FollowService {

    boolean follow(int userId, int entityType, int entityId);

    boolean unfollow(int userId, int entityType, int entityId);

    boolean isfollower(int userId,int entityType, int entityId);

    List<Integer> getFollowers(int entityType , int entityId, int count);

    List<Integer> getFollowers(int entityType, int entityId, int offset, int count);

    List<Integer> getFollowees(int userId , int entityType, int count);

    List<Integer> getFollowees(int userId , int entityType, int offset, int count);

    long getFollowerCount(int entityType, int entityId);

    long getFolloweeCount(int userId, int entityType) ;



}
