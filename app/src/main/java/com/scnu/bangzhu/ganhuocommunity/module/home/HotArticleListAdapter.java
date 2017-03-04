package com.scnu.bangzhu.ganhuocommunity.module.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.scnu.bangzhu.ganhuocommunity.R;
import com.scnu.bangzhu.ganhuocommunity.model.Article;

import java.util.List;

/**
 * Created by chenjianbang on 2017/1/6.
 */
public class HotArticleListAdapter extends BaseAdapter {
    private Context mContext;
    private List<Article> mHotArticleList;

    public HotArticleListAdapter(Context context, List<Article> list) {
        mContext = context;
        mHotArticleList = list;
    }

    @Override
    public int getCount() {
        return mHotArticleList.size();
    }

    @Override
    public Object getItem(int position) {
        return mHotArticleList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_hot_article_list_item, parent, false);
            viewHolder.hotArticleTitle = (TextView) convertView.findViewById(R.id.hot_article_item_title_textView);
            viewHolder.hotArticleLove = (TextView) convertView.findViewById(R.id.hot_article_item_love_textView);
            viewHolder.hotArticleAuthor = (TextView) convertView.findViewById(R.id.hot_article_item_author_textView);
            viewHolder.hotArticleTime = (TextView) convertView.findViewById(R.id.hot_article_item_time_textView);
            viewHolder.hotArticleImage = (ImageView) convertView.findViewById(R.id.hot_article_item_image_imageView);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Article article = mHotArticleList.get(position);
        viewHolder.hotArticleTitle.setText(article.getTitle());
        viewHolder.hotArticleLove.setText(" " + article.getLikesCount()+"");
        viewHolder.hotArticleAuthor.setText(" " + article.getAuthor().getUsername());
        viewHolder.hotArticleTime.setText(" " + article.getCreatedAt());

        String imageUrl = getUrlString(article.getImageUrl());
//        String imageUrl = url.substring(1, url.length()-1);

        Glide.with(mContext)
                .load(imageUrl)
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .crossFade()
                .into(viewHolder.hotArticleImage);
        return convertView;
    }

    private String getUrlString(String url) {
        while(url.contains("\"")) {
            int index = url.indexOf("\"");
            url = url.substring(index+1, url.length()-1);
        }
        return url;
    }

    public final class ViewHolder {
        public TextView hotArticleTitle;
        public TextView hotArticleLove;
        public TextView hotArticleAuthor;
        public TextView hotArticleTime;
        public ImageView hotArticleImage;
    }
}
