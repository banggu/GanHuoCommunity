package com.scnu.bangzhu.ganhuocommunity.module.home.commentcommunity;

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
public class CommentCommunityModelImpl implements CommentCommunityModel {
    private CommentCommunityPresenter mPresenter;

    public CommentCommunityModelImpl(CommentCommunityPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void queryTotalPageNum(final PageModel pageModel) {
        BmobQuery<Article> query = new BmobQuery<>();
        StringBuffer sql = new StringBuffer();
        sql.append("select include author,* from Article where type = " + "'" + "我要吐槽" + "'");
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
    public void loadArticleList(final PageModel pageModel) {
        BmobQuery<Article> query = new BmobQuery<>();
        query.order("-createdAt");
        StringBuffer sql = new StringBuffer();
        sql.append("select include author,* from Article where type = " + "'" + "我要吐槽" + "'");
        //加载更多
        if(pageModel.actionType == CommentCommunityFragment.STATE_MORE) {
            int page = pageModel.curPage + 1;
            // 跳过之前页数并去掉重复数据
            sql.append(" limit " + (page * pageModel.limit) + "," + pageModel.limit);
        } else {
            sql.append(" limit " + "0," + pageModel.limit);
        }
        query.doSQLQuery(sql.toString(), new SQLQueryListener<Article>(){

			@Override
			public void done(BmobQueryResult<Article> result, BmobException e) {
				if(e ==null){
					List<Article> list = (List<Article>) result.getResults();
					if(list!=null && list.size()>0){
                        int curPage = pageModel.curPage;
                        if(pageModel.actionType == CommentCommunityFragment.STATE_REFRESH) {
                            curPage = 1;
                        } else {
                            curPage++;
                        }
                        pageModel.curPage = curPage;
                        mPresenter.loadArticleListSuccess(list);
					}else{
                        mPresenter.loadArticleListFailtrue();
					}
				}else{
                    mPresenter.loadArticleListFailtrue();
				}
			}
		});
    }
}
