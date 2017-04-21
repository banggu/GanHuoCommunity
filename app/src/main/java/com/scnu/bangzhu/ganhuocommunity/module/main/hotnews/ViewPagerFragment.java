package com.scnu.bangzhu.ganhuocommunity.module.main.hotnews;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.scnu.bangzhu.ganhuocommunity.R;
import com.scnu.bangzhu.ganhuocommunity.model.Article;
import com.scnu.bangzhu.ganhuocommunity.module.main.articledetail.ArticleDetailsActivity;
import com.scnu.bangzhu.ganhuocommunity.util.StringUtil;

/**
 * Created by bangzhu on 2017/3/7.
 */

public class ViewPagerFragment extends Fragment {
    private View mView;
    private ImageView mArticleImage;
    private TextView mContent;
    private Article mArticle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.layout_viewpager_fragment, container, false);
        init();
        initView();
        initEvent();
        setContent();
        return mView;
    }

    private void init() {
        Bundle bundle = getArguments();
        mArticle = (Article) bundle.getSerializable("article");
    }

    private void initView() {
        mArticleImage = (ImageView) mView.findViewById(R.id.viewpager_fragment_image);
        mContent = (TextView) mView.findViewById(R.id.viewpager_fragment_content);
    }

    private void initEvent() {
        mArticleImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArticleDetailsActivity.startMe(getActivity(), mArticle);
            }
        });
    }

    private void setContent() {
        Glide.with(getActivity())
                .load(StringUtil.getOriginUrl(mArticle.getImageUrl()))
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .crossFade()
                .into(mArticleImage);
        mContent.setText(mArticle.getTitle());
    }
}
