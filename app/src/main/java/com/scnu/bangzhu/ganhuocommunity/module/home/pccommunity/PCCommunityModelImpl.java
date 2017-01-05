package com.scnu.bangzhu.ganhuocommunity.module.home.pccommunity;

import android.util.Log;

import com.scnu.bangzhu.ganhuocommunity.model.Article;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
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
    public void loadHotArticleList() {

    }

    @Override
    public void loadArticleList() {
        childQuery();
        BmobQuery<Article> query = new BmobQuery<>();
        String sql = "select include author,* from Article where type = " + "'" + "PC问题" + "'";
        query.doSQLQuery(sql, new SQLQueryListener<Article>(){

			@Override
			public void done(BmobQueryResult<Article> result, BmobException e) {
				if(e ==null){
					List<Article> list = (List<Article>) result.getResults();
					if(list!=null && list.size()>0){
                        for (Article article : list) {
                            Log.i("HZWING", article.getAuthor().getUsername()+"\n"+article.getCreatedAt()+"\n"+article.getImageUrl());
                        }
                        mPresenter.loadArticleListSuccess(list);
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

    private void childQuery() {
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
                    }else{
                        Log.i("smile", "查询成功，无数据返回");
                    }
                }else{
                    Log.i("smile", "错误码："+e.getErrorCode()+"，错误描述："+e.getMessage());
                }
            }
        });
    }
}
