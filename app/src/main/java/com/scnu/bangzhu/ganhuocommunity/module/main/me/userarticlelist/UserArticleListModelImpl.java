package com.scnu.bangzhu.ganhuocommunity.module.main.me.userarticlelist;

import android.util.Log;

import com.scnu.bangzhu.ganhuocommunity.model.Article;
import com.scnu.bangzhu.ganhuocommunity.model.CollectRead;
import com.scnu.bangzhu.ganhuocommunity.model.MyUser;
import com.scnu.bangzhu.ganhuocommunity.model.PageModel;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;

/**
 * Created by bangzhu on 2017/3/26.
 */

public class UserArticleListModelImpl implements UserArticleListModel {
    private UserArticleListPresenter mPresenter;

    public UserArticleListModelImpl(UserArticleListPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void getPageNum(final String actionType, final PageModel pageModel) {
        MyUser user = BmobUser.getCurrentUser(MyUser.class);
        BmobQuery<CollectRead> query = new BmobQuery<CollectRead>();
        query.order("-createdAt");
        StringBuffer sql = new StringBuffer();
        sql.append("select * from CollectRead where userId = " + "'" + user.getObjectId() + "'");
        //加载更多
        query.doSQLQuery(sql.toString(), new SQLQueryListener<CollectRead>() {
            @Override
            public void done(BmobQueryResult<CollectRead> result, BmobException e) {
                if(e == null) {
                    List<CollectRead> list = (List<CollectRead>) result.getResults();
                    //找得到，说明订阅了
                    if(list.size() > 0) {
                        BmobQuery<Article> query = new BmobQuery<Article>();
                        StringBuffer sql = new StringBuffer();
                        sql.append("select * from Article where related  "+ actionType + " to pointer(" + "'" + "CollectRead" + "'" + "," + "'" + list.get(0).getObjectId() + "'"  + ")");
                        query.doSQLQuery(sql.toString(), new SQLQueryListener<Article>() {
                            @Override
                            public void done(BmobQueryResult<Article> result, BmobException e) {
                                if(e == null) {
                                    List<Article> list = result.getResults();
                                    int count  = list.size();
                                    if(count%pageModel.limit == 0) {
                                        pageModel.pageNum = count/pageModel.limit;
                                    } else {
                                        pageModel.pageNum = count/pageModel.limit + 1;
                                    }
                                }
                            }
                        });
                    }
                }
            }
        });
    }

    @Override
    public void loadArticleList(final String actionType, final PageModel pageModel) {
        MyUser user = BmobUser.getCurrentUser(MyUser.class);
        BmobQuery<CollectRead> query = new BmobQuery<CollectRead>();
        query.order("-createdAt");
        StringBuffer sql = new StringBuffer();
        sql.append("select * from CollectRead where userId = " + "'" + user.getObjectId() + "'");
        //加载更多
        query.doSQLQuery(sql.toString(), new SQLQueryListener<CollectRead>() {
            @Override
            public void done(BmobQueryResult<CollectRead> result, BmobException e) {
                if(e == null) {
                    List<CollectRead> list = (List<CollectRead>) result.getResults();
                    //找得到，说明订阅了
                    if(list.size() > 0) {
                        BmobQuery<Article> query = new BmobQuery<Article>();
                        StringBuffer sql = new StringBuffer();
                        sql.append("select * from Article where related  "+ actionType + " to pointer(" + "'" + "CollectRead" + "'" + "," + "'" + list.get(0).getObjectId() + "'"  + ")");
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
                                    List<Article> list = result.getResults();
                                    if(list!=null && list.size()>0){
                                        int curPage = pageModel.curPage;
                                        if(pageModel.actionType == 0) {
                                            curPage = 0;
                                        } else {
                                            curPage++;
                                        }
                                        pageModel.curPage = curPage;
                                        mPresenter.loadArticleListSuccess(list);
                                    }else{
                                        Log.i("HZWING", "查询成功，无数据返回");
                                    }
                                }
                            }
                        });
                    }
                }
            }
        });
    }

}
