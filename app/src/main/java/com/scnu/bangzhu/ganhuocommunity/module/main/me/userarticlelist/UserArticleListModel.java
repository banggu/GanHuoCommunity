package com.scnu.bangzhu.ganhuocommunity.module.main.me.userarticlelist;

import com.scnu.bangzhu.ganhuocommunity.model.PageModel;

/**
 * Created by bangzhu on 2017/3/26.
 */

public interface UserArticleListModel {
    void getPageNum(String actionType, PageModel pageModel);

    void loadArticleList(String actionType, PageModel pageModel);

}
