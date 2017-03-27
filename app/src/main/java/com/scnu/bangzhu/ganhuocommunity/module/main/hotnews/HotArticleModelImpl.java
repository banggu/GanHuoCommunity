package com.scnu.bangzhu.ganhuocommunity.module.main.hotnews;

import android.os.Message;
import android.util.Log;

import com.scnu.bangzhu.ganhuocommunity.model.Article;
import com.scnu.bangzhu.ganhuocommunity.model.PageModel;
import com.scnu.bangzhu.ganhuocommunity.module.home.pccommunity.PCCommunityFragment;

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
    public void getPageNum(final PageModel pageModel) {
        BmobQuery<Article> query = new BmobQuery<>();
        StringBuffer sql = new StringBuffer();
        sql.append("select include author,* from Article "  + " where likesCount > 10 " + " order by likesCount desc ");
        query.doSQLQuery(sql.toString(), new SQLQueryListener<Article>() {
            @Override
            public void done(BmobQueryResult<Article> result, BmobException e) {
                List<Article> list = (List<Article>) result.getResults();
                int count  = list.size();
                if(count%pageModel.limit == 0) {
                    pageModel.pageNum = count/pageModel.limit;
                } else {
                    pageModel.pageNum = count/pageModel.limit + 1;
                }
            }
        });
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
    public void loadArticle(final PageModel pageModel) {
        BmobQuery<Article> query = new BmobQuery<>();
        StringBuffer sql = new StringBuffer();
        sql.append("select include author,* from Article "  + " where likesCount > 10 " + " order by likesCount desc ");
        if(pageModel.actionType == 1) {
            int page = pageModel.curPage + 1;
            sql.append(" limit " + (page * pageModel.limit) + "," + pageModel.limit);
        } else {
            sql.append(" limit " + "0," + pageModel.limit);
        }

        query.doSQLQuery(sql.toString(), new SQLQueryListener<Article>() {
            @Override
            public void done(BmobQueryResult<Article> result, BmobException e) {
                if(e == null) {
                    List<Article> list = (List<Article>) result.getResults();
                    if(list!=null && list.size()>0){
                        int curPage = pageModel.curPage;
                        if(pageModel.actionType == 0) {
                            curPage = 0;
                        } else {
                            curPage++;
                        }
                        pageModel.curPage = curPage;
                        mPresenter.loadArticleSuccess(list);
                    }else{
                        Log.i("HZWING", "查询成功，无数据返回");
                    }
                }
            }
        });
    }
}
