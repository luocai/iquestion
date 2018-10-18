package com.iquestion.controller;

import com.iquestion.common.Constant;
import com.iquestion.pojo.Feed;
import com.iquestion.pojo.HostHolder;
import com.iquestion.service.FeedService;
import com.iquestion.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@Controller
public class FeedController {

    @Autowired
    private FollowService followService;

    @Autowired
    private FeedService feedService;

    //拉 登录打开页面的时候根据关注的实体动态生成timeline
    @RequestMapping(path = {"/pullfeeds"}, method = {RequestMethod.GET, RequestMethod.POST})
    private String getPullFeeds(Model model) {
        int localUserId = HostHolder.getUser() != null ? HostHolder.getUser().getId() : 0;
        List<Integer> followees = new ArrayList<>();
        if (localUserId != 0) {
            // 关注的人
            followees = followService.getFollowees(localUserId, Constant.ENTITY_USER, Integer.MAX_VALUE);
        }
        List<Feed> feeds = feedService.getUserFeeds(Integer.MAX_VALUE, followees, 10);
        model.addAttribute("feeds", feeds);
        return "feeds";
    }
}
