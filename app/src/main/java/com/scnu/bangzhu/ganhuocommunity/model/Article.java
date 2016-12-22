package com.scnu.bangzhu.ganhuocommunity.model;

import cn.bmob.v3.BmobObject;

/**
 * Created by chenjianbang on 2016/12/15.
 */
public class Article extends BmobObject{
    private String user;
    private String title;
    private String type;
    private String imageUrl;
    private String content;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

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
}
