package com.scnu.bangzhu.ganhuocommunity.module.home.commentcommunity;

import com.scnu.bangzhu.ganhuocommunity.model.Article;

import java.util.List;

/**
 * Created by chenjianbang on 2016/12/26.
 */
public interface CommentCommunityPresenter {
    void queryTotalPageNum(int limit);

    void queryPageNumSuccess(int pageNum);

    void queryPageNumFailtrue();

    void loadArticleList(final int page, final int limit, final int actionType);

    void loadArticleListSuccess(int curPage, int actionType, List<Article> list);

    void loadArticleListFailtrue();
}
