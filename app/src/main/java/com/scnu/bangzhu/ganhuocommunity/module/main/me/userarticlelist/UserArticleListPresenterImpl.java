package com.scnu.bangzhu.ganhuocommunity.module.main.me.userarticlelist;

import com.scnu.bangzhu.ganhuocommunity.model.Article;
import com.scnu.bangzhu.ganhuocommunity.model.PageModel;

import java.util.List;

/**
 * Created by bangzhu on 2017/3/26.
 */

public class UserArticleListPresenterImpl implements UserArticleListPresenter {
    private UserArticleListView mView;
    private UserArticleListModel mModel;

    public UserArticleListPresenterImpl(UserArticleListView view) {
        mView = view;
        mModel = new UserArticleListModelImpl(this);
    }

    @Override
    public void getPageNum(String actionType, PageModel pageModel) {
        mModel.getPageNum(actionType, pageModel);
    }

    @Override
    public void loadArticleList(String actionType, PageModel pageModel) {
        mModel.loadArticleList(actionType, pageModel);
    }

    @Override
    public void loadArticleListSuccess(List<Article> articleList) {
        mView.refreshArticle(articleList);
    }

    @Override
    public void loadArticleFailtrue() {

    }
}
