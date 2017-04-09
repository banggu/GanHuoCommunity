package com.scnu.bangzhu.ganhuocommunity.module.main.me.usercreatedarticle;

import com.scnu.bangzhu.ganhuocommunity.model.ArticleFlag;
import com.scnu.bangzhu.ganhuocommunity.model.MyUserFlag;
import com.scnu.bangzhu.ganhuocommunity.module.main.me.collecteduserlist.UserListAdapter;

import java.util.List;

/**
 * Created by bangzhu on 2017/4/1.
 */

public interface UserCreatedArticlePresenter {
    void loadArticleList();

    void loadArticleListSuccess(List<ArticleFlag> userList);

    void loadArticleListFailtrue();

    void updateSubscribe(ArticleListEditableAdapter adapter);

    void updateSubscribeSuccess();

    void updateSubscribeFailtrue();
}
