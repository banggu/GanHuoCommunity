package com.scnu.bangzhu.ganhuocommunity.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bangzhu on 2017/3/24.
 */

public class MessageItem implements Serializable {
    private static final long serialVersionUID = 519067123721295773L;

    private MyUser user;
    private int msgNum;
    private ArrayList<Article> articleList;

    public MessageItem(MyUser user, int msgNum, ArrayList<Article> articleList) {
        this.user = user;
        this.msgNum = msgNum;
        this.articleList = articleList;
    }

    public MyUser getUser() {
        return user;
    }

    public void setUser(MyUser user) {
        this.user = user;
    }

    public int getMsgNum() {
        return msgNum;
    }

    public void setMsgNum(int msgNum) {
        this.msgNum = msgNum;
    }

    public ArrayList<Article> getArticleList() {
        return articleList;
    }

    public void setArticleList(ArrayList<Article> articleList) {
        this.articleList = articleList;
    }
}
