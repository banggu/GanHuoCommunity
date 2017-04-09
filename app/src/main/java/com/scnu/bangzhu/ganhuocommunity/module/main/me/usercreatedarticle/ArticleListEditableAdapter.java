package com.scnu.bangzhu.ganhuocommunity.module.main.me.usercreatedarticle;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.scnu.bangzhu.ganhuocommunity.R;
import com.scnu.bangzhu.ganhuocommunity.model.Article;
import com.scnu.bangzhu.ganhuocommunity.model.ArticleFlag;

import java.util.List;

/**
 * Created by bangzhu on 2017/4/1.
 */

public class ArticleListEditableAdapter extends BaseAdapter {
    private List<ArticleFlag> mArticleFlagList;
    private Context mContext;
    private ViewHolder mViewHolder;
    private boolean isShowFollow;

    public ArticleListEditableAdapter(Context context, List<ArticleFlag> list, boolean isShowFollow) {
        mContext = context;
        mArticleFlagList = list;
        this.isShowFollow = isShowFollow;
    }

    @Override
    public int getCount() {
        return mArticleFlagList.size();
    }

    @Override
    public Object getItem(int position) {
        return mArticleFlagList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_article_list_editable, parent, false);
            viewHolder.articleImage = (ImageView) convertView.findViewById(R.id.article_item_image_imageView);
            viewHolder.articleTitle = (TextView) convertView.findViewById(R.id.article_item_title_textView);
            viewHolder.articleTip = (TextView) convertView.findViewById(R.id.article_item_tip_textView);
            viewHolder.delect = (TextView) convertView.findViewById(R.id.edit_delete);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        ArticleFlag articleFlag = mArticleFlagList.get(position);
        String url = articleFlag.article.getImageUrl();
        String imageUrl = "";
        if(!TextUtils.isEmpty(url)) {
            imageUrl = url.substring(1, url.length()-1);
        }
        Glide.with(mContext)
                .load(imageUrl)
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .crossFade()
                .into(viewHolder.articleImage);
        viewHolder.articleTitle.setText(articleFlag.article.getTitle());
        String tip = articleFlag.article.getLikesCount() + "人收藏· " + articleFlag.article.getAuthor().getUsername() + "· " + articleFlag.article.getCreatedAt();
        viewHolder.articleTip.setText(tip);


        if(!isShowFollow) {
            viewHolder.delect.setVisibility(View.GONE);
        } else {
            viewHolder.delect.setVisibility(View.VISIBLE);
            setUndelete(viewHolder.delect, position);
        }

        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.delect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(finalViewHolder.isFollowed) {
                    setUndelete(finalViewHolder.delect, position);
                    finalViewHolder.isFollowed = false;
                } else {
                    setDelete(finalViewHolder.delect, position);
                    finalViewHolder.isFollowed = true;
                }
            }
        });
        return convertView;
    }

    private void setDelete(TextView textView, int position) {
        textView.setBackgroundResource(R.drawable.border_selected_red_bg);
        Drawable drawable = mContext.getResources().getDrawable(R.drawable.ic_checked);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());//必须设置图片大小，否则不显示
        textView.setCompoundDrawables(drawable, null, null, null);
        textView.setText(mContext.getResources().getString(R.string.deleted));
        textView.setTextColor(mContext.getResources().getColor(R.color.colorPrimaryWhite));
        mArticleFlagList.get(position).flag = true;
    }

    private void setUndelete(TextView textView, int position) {
        textView.setBackgroundResource(R.drawable.border_primary_red_bd);
        textView.setText(mContext.getResources().getString(R.string.delete));
        textView.setTextColor(mContext.getResources().getColor(R.color.primary_red));
        mArticleFlagList.get(position).flag = false;
    }

    public final class ViewHolder {
        public ImageView articleImage;
        public TextView articleTitle;
        public TextView articleTip;
        public TextView delect;
        public boolean isFollowed = false;
    }
}
