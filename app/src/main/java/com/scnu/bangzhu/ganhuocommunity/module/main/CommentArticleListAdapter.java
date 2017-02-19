package com.scnu.bangzhu.ganhuocommunity.module.main;

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
import com.scnu.bangzhu.ganhuocommunity.model.Comment;

import java.util.List;

/**
 * Created by bangzhu on 2017/2/15.
 */
public class CommentArticleListAdapter extends BaseAdapter {
    private Context mContext;
    private List<Comment> mCommentList;

    public CommentArticleListAdapter(Context context, List<Comment> commentList) {
        mContext = context;
        mCommentList = commentList;
    }

    @Override
    public int getCount() {
        return mCommentList.size();
    }

    @Override
    public Object getItem(int position) {
        return mCommentList.get(position);
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
            viewHolder.relevantArticleTitle = (TextView) convertView.findViewById(R.id.hot_article_item_title_textView);
            viewHolder.relevantArticleLove = (TextView) convertView.findViewById(R.id.hot_article_item_love_textView);
            viewHolder.relevantArticleAuthor = (TextView) convertView.findViewById(R.id.hot_article_item_author_textView);
            viewHolder.relevantArticleTime = (TextView) convertView.findViewById(R.id.hot_article_item_time_textView);
            viewHolder.relevantArticleImage = (ImageView) convertView.findViewById(R.id.hot_article_item_image_imageView);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Comment comment = mCommentList.get(position);
        Article article = comment.getArticle();
        viewHolder.relevantArticleTitle.setText(article.getTitle());
        viewHolder.relevantArticleLove.setText(" " + article.getLikesCount()+"");
        viewHolder.relevantArticleAuthor.setText(" " + article.getAuthor().getUsername());
        viewHolder.relevantArticleTime.setText(" " + article.getCreatedAt());

        String url = article.getImageUrl();
        String imageUrl = url.substring(1, url.length()-1);
        Glide.with(mContext)
                .load(imageUrl)
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .crossFade()
                .into(viewHolder.relevantArticleImage);

        return convertView;
    }

    public final class ViewHolder {
        public TextView relevantArticleTitle;
        public TextView relevantArticleLove;
        public TextView relevantArticleAuthor;
        public TextView relevantArticleTime;
        public ImageView relevantArticleImage;
    }
}
