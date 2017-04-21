package com.scnu.bangzhu.ganhuocommunity.module.home.pccommunity;

import android.os.Message;
import android.util.Log;

import com.scnu.bangzhu.ganhuocommunity.model.Article;
import com.scnu.bangzhu.ganhuocommunity.model.PageModel;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;

/**
 * Created by chenjianbang on 2016/12/26.
 */
public class PCCommunityModelImpl implements PCCommunityModel{
    private PCCommunityPresenter mPresenter;

    public PCCommunityModelImpl(PCCommunityPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void queryTotalPageNum(final PageModel pageModel) {
        BmobQuery<Article> query = new BmobQuery<>();
        StringBuffer sql = new StringBuffer();
        sql.append("select include author,* from Article where type = " + "'" + "PC问题" + "'");
        query.doSQLQuery(sql.toString(), new SQLQueryListener<Article>() {
            @Override
            public void done(BmobQueryResult<Article> result, BmobException e) {
                if(e == null) {
                    List<Article> list = (List<Article>) result.getResults();
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

    @Override
    public void loadHotArticleList() {
        BmobQuery<Article> query = new BmobQuery<>();
        String sql = "select include author,* from Article where type = " + "'" + "PC问题" + "'" + " order by " +"likesCount " + "desc";
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

    //加载PC问题文章列表
    @Override
    public void loadArticleList(final PageModel pageModel) {
        BmobQuery<Article> query = new BmobQuery<>();
        query.order("-createdAt");
        StringBuffer sql = new StringBuffer();
        sql.append("select include author,* from Article where type = " + "'" + "PC问题" + "'");
        //加载更多
        if(pageModel.actionType == PCCommunityFragment.STATE_MORE) {
            int page = pageModel.curPage + 1;
            // 跳过之前页数并去掉重复数据
            sql.append(" limit " + (page*pageModel.limit) + "," + pageModel.limit);
        } else {
            sql.append(" limit " + "0," + pageModel.limit);
        }
        query.doSQLQuery(sql.toString(), new SQLQueryListener<Article>(){

			@Override
			public void done(BmobQueryResult<Article> result, BmobException e) {
				if(e == null){
					List<Article> list = (List<Article>) result.getResults();
					if(list!=null && list.size()>0){
                        int curPage = pageModel.curPage;
                        if(pageModel.actionType == PCCommunityFragment.STATE_REFRESH) {
                            curPage = 0;
                        } else {
                            curPage++;
                        }
                        pageModel.curPage = curPage;
                        mPresenter.loadArticleListSuccess(list);
					}else{
						Log.i("HZWING", "查询成功，无数据返回");
                        mPresenter.loadArticleListFailtrue();
					}
				}else{
					Log.i("HZWING", "错误码："+e.getErrorCode()+"，错误描述："+e.getMessage());
                    mPresenter.loadArticleListFailtrue();
				}
			}
		});
    }
}
