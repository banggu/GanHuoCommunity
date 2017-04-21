package com.scnu.bangzhu.ganhuocommunity.module.home.pccommunity;

import com.scnu.bangzhu.ganhuocommunity.model.Article;
import com.scnu.bangzhu.ganhuocommunity.model.PageModel;

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
    public void queryTotalPageNum(PageModel pageModel) {
        mModel.queryTotalPageNum(pageModel);
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
    public void loadArticleList(PageModel pageModel) {
        mModel.loadArticleList(pageModel);
    }

    @Override
    public void loadArticleListSuccess(List<Article> list) {
        mView.refreshArticleList(list);
    }

    @Override
    public void loadArticleListFailtrue() {

    }
}
