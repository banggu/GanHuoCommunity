package com.scnu.bangzhu.ganhuocommunity.module.main.me.collecteduserlist;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.scnu.bangzhu.ganhuocommunity.R;
import com.scnu.bangzhu.ganhuocommunity.model.MyUser;
import com.scnu.bangzhu.ganhuocommunity.model.MyUserFlag;
import com.scnu.bangzhu.ganhuocommunity.util.StringUtil;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by bangzhu on 2017/3/26.
 */

public class UserListAdapter extends BaseAdapter {
    private Context mContext;
    private List<MyUserFlag> mUserList;
    private ViewHolder mViewHolder;
    private boolean isShowFollow;

    public UserListAdapter(Context context, List<MyUserFlag> userList, boolean isShowFollow) {
        mContext = context;
        mUserList = userList;
        this.isShowFollow = isShowFollow;
    }

    @Override
    public int getCount() {
        return mUserList.size();
    }

    @Override
    public Object getItem(int position) {
        return mUserList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_user_list_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.userAvatar = (CircleImageView) convertView.findViewById(R.id.author_details_avatar);
            viewHolder.userName = (TextView) convertView.findViewById(R.id.author_details_name);
            viewHolder.follow = (TextView) convertView.findViewById(R.id.author_details_follow);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        MyUserFlag userFlag = mUserList.get(position);
        String imageUrl = StringUtil.getOriginUrl(userFlag.user.getUserAvatar());
        Glide.with(mContext)
                .load(imageUrl)
                .centerCrop()
                .placeholder(R.drawable.ic_contacts)
                .crossFade()
                .into(viewHolder.userAvatar);

        viewHolder.userName.setText(userFlag.user.getUsername());

        if(!isShowFollow) {
            viewHolder.follow.setVisibility(View.GONE);
        } else {
            viewHolder.follow.setVisibility(View.VISIBLE);
            setFollow(viewHolder.follow, position);
        }

        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(finalViewHolder.isFollowed) {
                    setUnfollow(finalViewHolder.follow, position);
                    finalViewHolder.isFollowed = false;
                } else {
                    setFollow(finalViewHolder.follow, position);
                    finalViewHolder.isFollowed = true;
                }
            }
        });
        return convertView;
    }

    private void setFollow(TextView textView, int position) {
        textView.setBackgroundResource(R.drawable.border_checked_bg);
        Drawable drawable = mContext.getResources().getDrawable(R.drawable.ic_checked);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());//必须设置图片大小，否则不显示
        textView.setCompoundDrawables(drawable, null, null, null);
        textView.setText(mContext.getResources().getString(R.string.followed));
        textView.setTextColor(mContext.getResources().getColor(R.color.colorPrimaryWhite));
        mUserList.get(position).flag = true;
    }

    private void setUnfollow(TextView textView, int position) {
        textView.setBackgroundResource(R.drawable.border_bg);
        Drawable drawable = mContext.getResources().getDrawable(R.drawable.ic_s_add);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());//必须设置图片大小，否则不显示
        textView.setCompoundDrawables(drawable, null, null, null);
        textView.setText(mContext.getResources().getString(R.string.follow));
        textView.setTextColor(mContext.getResources().getColor(R.color.primary_green));
        mUserList.get(position).flag = false;
    }

    final class ViewHolder {
        public CircleImageView userAvatar;
        public TextView userName;
        public TextView follow;
        public boolean isFollowed = true;
    }
}
