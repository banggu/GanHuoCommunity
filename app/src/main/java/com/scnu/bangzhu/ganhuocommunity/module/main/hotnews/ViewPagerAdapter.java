package com.scnu.bangzhu.ganhuocommunity.module.main.hotnews;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.scnu.bangzhu.ganhuocommunity.R;
import com.scnu.bangzhu.ganhuocommunity.model.Article;
import com.scnu.bangzhu.ganhuocommunity.module.main.articledetail.ArticleDetailsActivity;

import java.util.List;

/**
 * Created by bangzhu on 2016/8/21.
 */
public class ViewPagerAdapter extends PagerAdapter{
    private List<Article> mArticleList;
    private Context mContext;

    public ViewPagerAdapter(Context context, List<Article> viewList){
        mContext = context;
        mArticleList = viewList;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager)container).removeView((View)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        final Article article = mArticleList.get(position);
        ImageView imageView = new ImageView(mContext);

        String imageUrl = getUrlString(article.getImageUrl());
        Glide.with(mContext)
                .load(imageUrl)
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .crossFade()
                .into(imageView);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        imageView.setLayoutParams(params);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        ((ViewPager)container).addView(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArticleDetailsActivity.startMe(mContext, article);
            }
        });
        return imageView;
    }

    @Override
    public int getCount() {
        return mArticleList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == object);
    }

    private String getUrlString(String url) {
        while(url.contains("\"")) {
            int index = url.indexOf("\"");
            url = url.substring(index+1, url.length()-1);
        }
        return url;
    }
}
