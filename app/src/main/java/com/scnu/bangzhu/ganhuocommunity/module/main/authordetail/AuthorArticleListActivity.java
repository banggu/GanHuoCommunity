package com.scnu.bangzhu.ganhuocommunity.module.main.authordetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.scnu.bangzhu.ganhuocommunity.BaseActivity;
import com.scnu.bangzhu.ganhuocommunity.R;
import com.scnu.bangzhu.ganhuocommunity.model.Article;
import com.scnu.bangzhu.ganhuocommunity.module.home.ArticleListAdapter;
import com.scnu.bangzhu.ganhuocommunity.module.main.articledetail.ArticleDetailsActivity;

import java.util.ArrayList;

/**
 * Created by bangzhu on 2017/3/23.
 */

public class AuthorArticleListActivity extends BaseActivity {
    private ArrayList<Article> mArticleList;
    private ListView mArticleListView;
    private ArticleListAdapter mAdapter;
    private ImageView mBack;
    private TextView mTopTitle;
    private String mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_list);
        init();
        initView();
        bindView();
        setListener();
    }

    private void init() {
        Intent intent = getIntent();
        mArticleList = (ArrayList<Article>) intent.getSerializableExtra("articlelist");
        mTitle = intent.getStringExtra("title");
    }

    private void initView() {
        mBack = (ImageView) findViewById(R.id.back_header_back);
        mTopTitle = (TextView) findViewById(R.id.back_header_title);
        mArticleListView = (ListView) findViewById(R.id.article_list_listView);
    }

    private void bindView() {
        mAdapter = new ArticleListAdapter(this, mArticleList);
        mArticleListView.setAdapter(mAdapter);

        mTopTitle.setText(mTitle);
    }

    private void setListener() {
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mArticleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ArticleDetailsActivity.startMe(AuthorArticleListActivity.this, mArticleList.get(i));
            }
        });
    }

    public static void startMe(Context context, ArrayList<Article> articleList, String title) {
        Intent intent = new Intent(context, AuthorArticleListActivity.class);
        intent.putExtra("articlelist", articleList);
        intent.putExtra("title", title);
        context.startActivity(intent);
    }
}
