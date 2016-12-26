package com.scnu.bangzhu.ganhuocommunity.module.home.pccommunity;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.scnu.bangzhu.ganhuocommunity.R;
import com.scnu.bangzhu.ganhuocommunity.model.Article;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by chenjianbang on 2016/12/26.
 */
public class PCNewsListAdapter extends BaseAdapter{
    private List<Article> mArticleList;
    private Context mContext;

    public PCNewsListAdapter(Context context, List<Article> list) {
        mContext = context;
        mArticleList = list;
    }

    @Override
    public int getCount() {
        return mArticleList.size();
    }

    @Override
    public Object getItem(int position) {
        return mArticleList.get(position);
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_article_list_item, parent, false);
            viewHolder.articleImage = (ImageView) convertView.findViewById(R.id.article_item_image_imageView);
            viewHolder.articleTitle = (TextView) convertView.findViewById(R.id.article_item_title_textView);
            viewHolder.articleTip = (TextView) convertView.findViewById(R.id.article_item_tip_textView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Article article = mArticleList.get(position);
        Picasso.with(mContext)
                .load(article.getImageUrl())
                .resize(60, 60)
                .centerCrop()
                .into(viewHolder.articleImage);
        viewHolder.articleTitle.setText(article.getTitle());
        String tip = "0人收藏·" + article.getAuthor().getUsername() + "·" + article.getAuthor().getCreatedAt();
        viewHolder.articleTip.setText(tip);
        return convertView;
    }

    public final class ViewHolder {
        public ImageView articleImage;
        public TextView articleTitle;
        public TextView articleTip;
    }
}
