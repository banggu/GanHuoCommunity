package com.scnu.bangzhu.ganhuocommunity.module.home.commentcommunity;

import com.scnu.bangzhu.ganhuocommunity.model.Article;

import java.util.List;

/**
 * Created by chenjianbang on 2016/12/26.
 */
public interface CommentCommunityView {
    void showDataLoading();

    void showNetworkError();

    void setPageNum(int pageNum);

    void refreshArticleList(int curPage, int actionType, List<Article> list);

}
