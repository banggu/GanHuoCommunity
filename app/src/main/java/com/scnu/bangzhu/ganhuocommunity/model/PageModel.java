package com.scnu.bangzhu.ganhuocommunity.model;

/**
 * Created by bangzhu on 2017/3/26.
 */

public class PageModel {
    public int pageNum;
    public int curPage;
    public int limit;
    public int actionType;

    public PageModel(int pageNum, int curPage, int limit, int actionType) {
        this.pageNum = pageNum;
        this.curPage = curPage;
        this.limit = limit;
        this.actionType = actionType;
    }
}
