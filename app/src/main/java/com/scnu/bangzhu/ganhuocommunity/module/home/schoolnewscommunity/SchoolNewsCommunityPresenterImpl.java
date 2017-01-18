package com.scnu.bangzhu.ganhuocommunity.module.home.schoolnewscommunity;

import com.scnu.bangzhu.ganhuocommunity.model.Article;

import java.util.List;

/**
 * Created by chenjianbang on 2016/12/26.
 */
public class SchoolNewsCommunityPresenterImpl implements SchoolNewsCommunityPresenter {
    private SchoolNewsCommunityView mView;
    private SchoolNewsCommunityModel mModel;

    public SchoolNewsCommunityPresenterImpl(SchoolNewsCommunityView view) {
        mView = view;
        mModel = new SchoolNewsCommunityModelImpl(this);
    }

    @Override
    public void loadHotArticleList() {
        mModel.loadHotArticleList();
    }

    @Override
    public void loadHotArticleListSuccess(List<Article> list) {
        mView.refreshHotArticleList(list);
    }

    @Override
    public void loadHotArticleListFailtrue() {

    }

    @Override
    public void loadArticleList(final int page, final int limit, final int actionType) {
        mModel.loadArticleList(page, limit, actionType);
    }

    @Override
    public void loadArticleListSuccess(int curPage, int actionType, List<Article> list) {
        mView.refreshArticleList(curPage, actionType, list);
    }

    @Override
    public void loadArticleListFailtrue() {

    }
}
