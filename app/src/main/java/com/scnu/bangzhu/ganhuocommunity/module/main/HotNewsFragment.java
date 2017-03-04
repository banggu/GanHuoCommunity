package com.scnu.bangzhu.ganhuocommunity.module.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.scnu.bangzhu.ganhuocommunity.R;
import com.scnu.bangzhu.ganhuocommunity.model.Article;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;

/**
 * Created by bangzhu on 2016/12/5.
 */
public class HotNewsFragment extends Fragment {
    private View mView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_hot_module, container,false);
        initView();
        return mView;
    }

    private void initView() {
        Button loadHotArticle = (Button) mView.findViewById(R.id.btn_load_hot_article);
        loadHotArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadHotArticles();
            }
        });
    }

    private void loadHotArticles() {
        BmobQuery<Article> query = new BmobQuery<>();
        StringBuffer sql = new StringBuffer();
        sql.append("select include author,top 3 * from Article where type = " + "'" + "PC问题" + "'");
        query.doSQLQuery(sql.toString(), new SQLQueryListener<Article>() {
            @Override
            public void done(BmobQueryResult<Article> result, BmobException e) {
                List<Article> list = (List<Article>) result.getResults();
                int count  = list.size();
                String s = "select *\n" +
                        "from article\n" +
                        "group by ty\n" +
                        "having likeCount > 10\n" +
                        "order by likeCount desc";
            }
        });
    }

}
