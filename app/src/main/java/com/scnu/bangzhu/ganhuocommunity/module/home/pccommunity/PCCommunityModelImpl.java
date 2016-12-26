package com.scnu.bangzhu.ganhuocommunity.module.home.pccommunity;

import android.util.Log;

import com.scnu.bangzhu.ganhuocommunity.model.Article;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

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
        BmobQuery<Article> query = new BmobQuery<Article>();
        query.addWhereEqualTo("type", "计算机");
        //执行查询方法
        query.include("author");
        query.findObjects(new FindListener<Article>() {
            @Override
            public void done(List<Article> object, BmobException e) {
                if(e==null){
                    for (Article article : object) {
                        Log.i("HZWING", article.getAuthor().getUsername()+"\n"+article.getCreatedAt()+"\n"+article.getImageUrl());
                    }
                    mPresenter.loadArticleListSuccess(object);
                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                    mPresenter.loadArticleListFailtrue();
                }
            }
        });
    }
}
