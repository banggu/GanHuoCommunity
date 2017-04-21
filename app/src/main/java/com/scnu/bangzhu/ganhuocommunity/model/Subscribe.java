package com.scnu.bangzhu.ganhuocommunity.model;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by bangzhu on 2017/3/19.
 */

public class Subscribe extends BmobObject {
    private String authorId;
    private BmobRelation follow;
    private Integer followCount;
    private BmobRelation follower;
    private Integer followerCount;

    public Integer getFollowerCount() {
        return followerCount;
    }

    public void setFollowerCount(Integer followerCount) {
        this.followerCount = followerCount;
    }

    public BmobRelation getFollower() {
        return follower;
    }

    public void setFollower(BmobRelation follower) {
        this.follower = follower;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public BmobRelation getFollow() {
        return follow;
    }

    public void setFollow(BmobRelation follow) {
        this.follow = follow;
    }

    public Integer getFollowCount() {
        return followCount;
    }

    public void setFollowCount(Integer followCount) {
        this.followCount = followCount;
    }
}
