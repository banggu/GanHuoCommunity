package com.scnu.bangzhu.ganhuocommunity.module.home.commentcommunity;

import com.scnu.bangzhu.ganhuocommunity.model.PageModel;

/**
 * Created by chenjianbang on 2016/12/26.
 */
public interface CommentCommunityModel {
    void queryTotalPageNum(PageModel pageModel);

    void loadArticleList(PageModel pageModel);
}
