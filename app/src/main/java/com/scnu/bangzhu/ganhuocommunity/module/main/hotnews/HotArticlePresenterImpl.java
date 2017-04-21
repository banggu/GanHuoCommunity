package com.scnu.bangzhu.ganhuocommunity.module.main.hotnews;

import com.scnu.bangzhu.ganhuocommunity.model.Article;
import com.scnu.bangzhu.ganhuocommunity.model.PageModel;

import java.util.List;

/**
 * Created by bangzhu on 2017/3/4.
 */

public class HotArticlePresenterImpl implements HotArticlePresenter {
    private HotArticleView mView;
    private HotArticleModel mModel;

    public HotArticlePresenterImpl(HotArticleView view) {
        mView = view;
        mModel = new HotArticleModelImpl(this);
    }

    @Override
    public void getPageNum(PageModel pageModel) {
        mModel.getPageNum(pageModel);
    }

    @Override
    public void loadHotArticle() {
        mModel.loadHotArticle();
    }

    @Override
    public void loadHotArticleSuccess(List<Article> list) {
        mView.refreshHotArticle(list);
    }

    @Override
    public void loadHotArticleFailture() {

    }

    @Override
    public void loadArticle(PageModel pageModel) {
        mModel.loadArticle(pageModel);
    }

    @Override
    public void loadArticleSuccess(List<Article> list) {
        mView.refreshArticle(list);
    }

    @Override
    public void loadArticleFailture() {

    }
}
