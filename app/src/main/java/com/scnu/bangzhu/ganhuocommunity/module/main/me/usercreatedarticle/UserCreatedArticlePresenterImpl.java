package com.scnu.bangzhu.ganhuocommunity.module.main.me.usercreatedarticle;

import com.scnu.bangzhu.ganhuocommunity.model.ArticleFlag;

import java.util.List;

/**
 * Created by bangzhu on 2017/4/1.
 */

public class UserCreatedArticlePresenterImpl implements UserCreatedArticlePresenter {
    private UserCreatedArticleView mView;
    private UserCreatedArticleModel mModel;

    public UserCreatedArticlePresenterImpl(UserCreatedArticleView view) {
        mView = view;
        mModel = new UserCreatedArticleModelImpl(this);
    }

    @Override
    public void loadArticleList() {
        mModel.loadArticleList();
    }

    @Override
    public void loadArticleListSuccess(List<ArticleFlag> articleFlagListList) {
        mView.refreshUserList(articleFlagListList);
    }

    @Override
    public void loadArticleListFailtrue() {

    }

    @Override
    public void updateSubscribe(ArticleListEditableAdapter adapter) {
        mModel.updateSubscribe(adapter);
    }

    @Override
    public void updateSubscribeSuccess() {
        mView.updateSubscribeSuccess();
    }

    @Override
    public void updateSubscribeFailtrue() {

    }
}
