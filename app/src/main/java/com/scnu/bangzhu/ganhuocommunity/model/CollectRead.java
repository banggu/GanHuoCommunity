package com.scnu.bangzhu.ganhuocommunity.model;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by bangzhu on 2017/3/22.
 */

public class CollectRead extends BmobObject {
    private String userId;
    private BmobRelation collected;
    private Integer collectedCount;
    private BmobRelation read;
    private Integer readCount;
    private BmobRelation created;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public BmobRelation getCollected() {
        return collected;
    }

    public void setCollected(BmobRelation collected) {
        this.collected = collected;
    }

    public Integer getCollectedCount() {
        return collectedCount;
    }

    public void setCollectedCount(Integer collectedCount) {
        this.collectedCount = collectedCount;
    }

    public BmobRelation getRead() {
        return read;
    }

    public void setRead(BmobRelation read) {
        this.read = read;
    }

    public Integer getReadCount() {
        return readCount;
    }

    public void setReadCount(Integer readCount) {
        this.readCount = readCount;
    }

    public BmobRelation getCreated() {
        return created;
    }

    public void setCreated(BmobRelation created) {
        this.created = created;
    }
}
