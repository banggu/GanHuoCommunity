package com.scnu.bangzhu.ganhuocommunity.module.main.hotnews;

import com.scnu.bangzhu.ganhuocommunity.model.Article;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;

/**
 * Created by bangzhu on 2017/3/4.
 */

public class HotArticleModelImpl implements HotArticleModel {
    private HotArticlePresenter mPresenter;

    public HotArticleModelImpl(HotArticlePresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void loadHotArticle() {
        BmobQuery<Article> query = new BmobQuery<>();
        StringBuffer sql = new StringBuffer();
        sql.append("select include author,* from Article " + "order by likesCount desc " + "limit 4");
        query.doSQLQuery(sql.toString(), new SQLQueryListener<Article>() {
            @Override
            public void done(BmobQueryResult<Article> result, BmobException e) {
                List<Article> list = (List<Article>) result.getResults();
                mPresenter.loadHotArticleSuccess(list);
            }
        });
    }

    @Override
    public void loadArticle() {
        BmobQuery<Article> query = new BmobQuery<>();
        StringBuffer sql = new StringBuffer();
        sql.append("select * from Article "  + " where likesCount > 10 " + " order by likesCount desc ");
        query.doSQLQuery(sql.toString(), new SQLQueryListener<Article>() {
            @Override
            public void done(BmobQueryResult<Article> result, BmobException e) {
                List<Article> list = (List<Article>) result.getResults();
                mPresenter.loadArticleSuccess(list);
            }
        });
    }
}
