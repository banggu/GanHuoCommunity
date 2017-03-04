package com.scnu.bangzhu.ganhuocommunity.model;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by chenjianbang on 2016/12/15.
 */
public class Article extends BmobObject{
    private String title;
    private String type;
    private String imageUrl;
    private String content;
    private BmobUser author;
    private BmobRelation likes;
    private Integer  likesCount;
    private BmobRelation read;
    private Integer readCount;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public BmobUser getAuthor() {
        return author;
    }

    public void setAuthor(BmobUser author) {
        this.author = author;
    }

    public BmobRelation getLikes() {
        return likes;
    }

    public void setLikes(BmobRelation likes) {
        this.likes = likes;
    }

    public Integer  getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(Integer  likesCount) {
        this.likesCount = likesCount;
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
}
