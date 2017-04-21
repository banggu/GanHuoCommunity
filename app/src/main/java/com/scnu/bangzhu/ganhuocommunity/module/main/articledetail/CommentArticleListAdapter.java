package com.scnu.bangzhu.ganhuocommunity.module.main.articledetail;

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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_article_comment, parent, false);
            viewHolder.commentUserImage = (ImageView) convertView.findViewById(R.id.comment_user_image_imageView);
            viewHolder.commentUserName = (TextView) convertView.findViewById(R.id.comment_user_name_textView);
            viewHolder.commentUserContent = (TextView) convertView.findViewById(R.id.comment_user_content_textView);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Comment comment = mCommentList.get(position);
        Article article = comment.getArticle();
        viewHolder.commentUserName.setText(comment.getUser().getUsername());
        viewHolder.commentUserContent.setText(comment.getContent());

        String url = article.getImageUrl();
        String imageUrl = url.substring(1, url.length()-1);
        Glide.with(mContext)
                .load(imageUrl)
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .crossFade()
                .into(viewHolder.commentUserImage);

        return convertView;
    }

    public final class ViewHolder {
        public TextView commentUserName;
        public TextView commentUserContent;
        public ImageView commentUserImage;
    }
}
