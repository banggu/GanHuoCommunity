package com.scnu.bangzhu.ganhuocommunity.module.home.commentcommunity;

import com.scnu.bangzhu.ganhuocommunity.model.Article;

import java.util.List;

/**
 * Created by chenjianbang on 2016/12/26.
 */
public class CommentCommunityPresenterImpl implements CommentCommunityPresenter {
    private CommentCommunityView mView;
    private CommentCommunityModel mModel;

    public CommentCommunityPresenterImpl(CommentCommunityView view) {
        mView = view;
        mModel = new CommentCommunityModelImpl(this);
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
