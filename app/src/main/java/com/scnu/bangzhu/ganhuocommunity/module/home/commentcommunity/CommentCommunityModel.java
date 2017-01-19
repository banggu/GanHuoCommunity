package com.scnu.bangzhu.ganhuocommunity.module.home.commentcommunity;

/**
 * Created by chenjianbang on 2016/12/26.
 */
public interface CommentCommunityModel {
    void queryTotalPageNum(int limit);

    void loadArticleList(final int page, final int limit, final int actionType);
}
