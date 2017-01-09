package com.scnu.bangzhu.ganhuocommunity.module.home.coursecommunity;

import com.scnu.bangzhu.ganhuocommunity.model.Article;

import java.util.List;

/**
 * Created by chenjianbang on 2017/1/9.
 */
public class CourseCommunityPresenterImpl implements CourseCommunityPresenter {
    private CourseCommunityModel mModel;
    private CourseCommunityView mView;

    public CourseCommunityPresenterImpl(CourseCommunityView view) {
        mView = view;
        mModel = new CourseCommunityModelImpl(this);
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
    public void loadArticleList(int page, int limit, int actionType) {
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
