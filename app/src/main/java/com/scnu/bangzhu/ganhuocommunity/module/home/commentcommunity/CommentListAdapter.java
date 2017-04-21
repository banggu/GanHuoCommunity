package com.scnu.bangzhu.ganhuocommunity.module.home.commentcommunity;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Html;
import android.text.TextUtils;
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
import com.scnu.bangzhu.ganhuocommunity.model.Comment;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by chenjianbang on 2017/1/19.
 */

public class CommentListAdapter extends BaseAdapter implements View.OnClickListener{
    private List<Article> mArticleList;
    private Context mContext;
    private Article mArticle;
    private ViewHolder mViewHolder;

    public CommentListAdapter(Context context, List<Article> list) {
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
        mViewHolder = null;
        if(convertView == null) {
            mViewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_comment_list_item, parent, false);
            mViewHolder.commentImage = (ImageView) convertView.findViewById(R.id.commentitem_image_imageView);
            mViewHolder.commentUser = (TextView) convertView.findViewById(R.id.commentitem_user_textView);
            mViewHolder.commentTitle = (TextView) convertView.findViewById(R.id.commentitem_title_textView);
            mViewHolder.commentContent = (TextView) convertView.findViewById(R.id.commentitem_content_textView);
            mViewHolder.commentLove = (TextView) convertView.findViewById(R.id.commentitem_love_imageView);
            mViewHolder.commentRemark = (TextView) convertView.findViewById(R.id.commentitem_comment_imageView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        mArticle = mArticleList.get(position);
        String url = mArticle.getImageUrl();
        String imageUrl;
        if(TextUtils.isEmpty(url)) {
            imageUrl = "";
        } else {
            imageUrl = url.substring(1, url.length()-1);
        }
        Glide.with(mContext)
                .load(imageUrl)
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .crossFade()
                .into(mViewHolder.commentImage);
        mViewHolder.commentUser.setText(mArticle.getAuthor().getUsername());
        mViewHolder.commentTitle.setText(mArticle.getTitle());
        int start = mArticle.getContent().indexOf("<div");
        int end = mArticle.getContent().indexOf("</div>");
        String content = mArticle.getContent().substring(start, end+6);
        mViewHolder.commentContent.setText(Html.fromHtml(content, null, null));
        mViewHolder.commentLove.setOnClickListener(this);
        mViewHolder.commentRemark.setOnClickListener(this);
        return convertView;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.commentitem_love_imageView:
                bindRelation(mArticle);
                break;
            case R.id.commentitem_comment_imageView:
                break;
        }
    }

    //绑定关注关系
    private void bindRelation(Article a) {
        BmobUser user = BmobUser.getCurrentUser();
        final Article article = new Article();
        article.setObjectId(a.getObjectId());
        //将当前用户添加到Post表中的likes字段值中，表明当前用户喜欢该帖子
        BmobRelation relation = new BmobRelation();
        //将当前用户添加到多对多关联中
        relation.add(user);
        //多对多关联指向`post`的`likes`字段
        article.setLikes(relation);
        article.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    Log.i("HZWING","多对多关联添加成功");
                    getLikesCount(article);
                }else{
                    Log.i("HZWING","失败："+e.getMessage());
                }
            }

        });
    }

    private void getLikesCount(Article a) {
        BmobQuery<BmobUser> query = new BmobQuery<BmobUser>();
        final Article article = new Article();
        article.setObjectId(a.getObjectId());
        //likes是Post表中的字段，用来存储所有喜欢该帖子的用户
        query.addWhereRelatedTo("likes", new BmobPointer(article));
        query.findObjects(new FindListener<BmobUser>() {

            @Override
            public void done(List<BmobUser> object,BmobException e) {
                if(e==null){
                    Log.i("bmob","查询个数："+object.size());
                    //Drawable drawable = mContext.getResources().getDrawable(R.drawable.ic_love_follow);;
                    //mViewHolder.commentLove.setCompoundDrawables(drawable, null, null, null);
                    mViewHolder.commentLove.setText(String.valueOf(object.size()));
                    updateArticle(article, object.size());
                }else{
                    Log.i("bmob","失败："+e.getMessage());
                }
            }

        });
    }

    private void updateArticle(Article a, int num) {
        Article article = new Article();
        article.setLikesCount(num);
        article.update(a.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                Log.i("smile", "update success");
            }
        });
    }

    public final class ViewHolder {
        public ImageView commentImage;
        public TextView commentUser;
        public TextView commentTitle;
        public TextView commentContent;
        public TextView commentLove;
        public TextView commentRemark;
    }
}
