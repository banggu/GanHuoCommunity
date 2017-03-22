package com.scnu.bangzhu.ganhuocommunity.module.home.coursecommunity;

import android.util.Log;

import com.scnu.bangzhu.ganhuocommunity.model.Article;
import com.scnu.bangzhu.ganhuocommunity.module.home.pccommunity.PCCommunityFragment;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;

/**
 * Created by chenjianbang on 2017/1/9.
 */
public class CourseCommunityModelImpl implements CourseCommunityModel {
    private CourseCommunityPresenter mPresenter;

    public CourseCommunityModelImpl(CourseCommunityPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void queryTotalPageNum(final int limit) {
        BmobQuery<Article> query = new BmobQuery<>();
        StringBuffer sql = new StringBuffer();
        sql.append("select include author,* from Article where type = " + "'" + "选课相关" + "'");
        query.doSQLQuery(sql.toString(), new SQLQueryListener<Article>() {
            @Override
            public void done(BmobQueryResult<Article> result, BmobException e) {
                if(e == null) {
                    List<Article> list = (List<Article>) result.getResults();
                    int count  = list.size();
                    int pageNum = count/limit + 1;
                    mPresenter.queryPageNumSuccess(pageNum);
                }
            }
        });
    }

    @Override
    public void loadHotArticleList() {
        BmobQuery<Article> query = new BmobQuery<>();
        String sql = "select include author,* from Article where type = " + "'" + "选课相关" + "'" + " order by " +"likesCount " + "desc";
        query.setLimit(3);
        query.doSQLQuery(sql, new SQLQueryListener<Article>(){

            @Override
            public void done(BmobQueryResult<Article> result, BmobException e) {
                if(e ==null){
                    List<Article> list = (List<Article>) result.getResults();
                    if(list!=null && list.size()>0){
                        for (Article article : list) {
                            Log.i("smile", article.getTitle()+"\n"+article.getCreatedAt()+"\n"+article.getImageUrl());
                        }
                        List<Article> limitList = new ArrayList<Article>();
                        for(int i=0;i<3;i++) {
                            limitList.add(list.get(i));
                        }
                        mPresenter.loadHotArticleListSuccess(limitList);
                    }else{
                        Log.i("smile", "查询成功，无数据返回");
                    }
                }else{
                    Log.i("smile", "错误码："+e.getErrorCode()+"，错误描述："+e.getMessage());
                }
            }
        });
    }

    @Override
    public void loadArticleList(final int page, int limit, final int actionType) {
        BmobQuery<Article> query = new BmobQuery<>();
        query.order("-createdAt");
        StringBuffer sql = new StringBuffer();
        sql.append("select include author,* from Article where type = " + "'" + "选课相关" + "'");
        //加载更多
        if(actionType == PCCommunityFragment.STATE_MORE) {
            // 跳过之前页数并去掉重复数据
            sql.append(" limit " + (page*limit) + "," + limit);
        } else {
            sql.append(" limit " + "0," + limit);
        }
        query.doSQLQuery(sql.toString(), new SQLQueryListener<Article>(){

            @Override
            public void done(BmobQueryResult<Article> result, BmobException e) {
                if(e ==null){
                    List<Article> list = (List<Article>) result.getResults();
                    if(list!=null && list.size()>0){
                        for (Article article : list) {
                            Log.i("HZWING", article.getAuthor().getUsername()+"\n"+article.getCreatedAt()+"\n"+article.getImageUrl());
                        }
                        int curPage = page;
                        if(actionType == PCCommunityFragment.STATE_REFRESH) {
                            curPage = 1;
                        } else {
                            curPage++;
                        }
                        mPresenter.loadArticleListSuccess(curPage, actionType, list);
                    }else{
                        Log.i("HZWING", "查询成功，无数据返回");
                    }
                }else{
                    Log.i("HZWING", "错误码："+e.getErrorCode()+"，错误描述："+e.getMessage());
                    mPresenter.loadArticleListFailtrue();
                }
            }
        });
    }
}
