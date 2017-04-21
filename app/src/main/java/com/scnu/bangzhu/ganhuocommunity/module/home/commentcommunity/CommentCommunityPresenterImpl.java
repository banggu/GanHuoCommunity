package com.scnu.bangzhu.ganhuocommunity.module.home.commentcommunity;

import com.scnu.bangzhu.ganhuocommunity.model.Article;
import com.scnu.bangzhu.ganhuocommunity.model.PageModel;

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
