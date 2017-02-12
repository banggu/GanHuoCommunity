package com.scnu.bangzhu.ganhuocommunity.module.home.pccommunity;

import com.scnu.bangzhu.ganhuocommunity.model.Article;

import java.util.List;

/**
 * Created by chenjianbang on 2016/12/26.
 */
public class PCCommunityPresenterImpl implements PCCommunityPresenter{
    private PCCommunityView mView;
    private PCCommunityModel mModel;

    public PCCommunityPresenterImpl(PCCommunityView view) {
        mView = view;
        mModel = new PCCommunityModelImpl(this);
    }

    @Override
    public void queryTotalPageNum(int limit) {
        mModel.queryTotalPageNum(limit);
    }

    @Override
    public void queryPageNumSuccess(int pageNum) {
        mView.setPageNum(pageNum);
    }

    @Override
    public void queryPageNumFailtrue() {

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
    public void loadArticleList(final int page, final int limit, final int actionType, PCCommunityFragment.StaticHandler handler) {
        mModel.loadArticleList(page, limit, actionType, handler);
    }

    @Override
    public void loadArticleListSuccess(int curPage, int actionType, List<Article> list) {
        mView.setCurPage(curPage);
        mView.refreshArticleList(actionType, list);
    }

    @Override
    public void loadArticleListFailtrue() {

    }
}
