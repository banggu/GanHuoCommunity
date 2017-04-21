package com.scnu.bangzhu.ganhuocommunity.module.main.me.usercreatedarticle;

import com.scnu.bangzhu.ganhuocommunity.model.ArticleFlag;

import java.util.List;

/**
 * Created by bangzhu on 2017/4/1.
 */

public interface UserCreatedArticleView {
    void refreshUserList(List<ArticleFlag> userList);


    void updateSubscribeSuccess();
}
