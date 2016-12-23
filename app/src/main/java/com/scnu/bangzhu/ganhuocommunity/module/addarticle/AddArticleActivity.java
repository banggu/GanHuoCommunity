package com.scnu.bangzhu.ganhuocommunity.module.addarticle;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.scnu.bangzhu.ganhuocommunity.BaseActivity;
import com.scnu.bangzhu.ganhuocommunity.GanHuoCache;
import com.scnu.bangzhu.ganhuocommunity.R;
import com.scnu.bangzhu.ganhuocommunity.config.Contants;
import com.scnu.bangzhu.ganhuocommunity.module.main.MainActivity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import jp.wasabeef.richeditor.RichEditor;

/**
 * Created by chenjianbang on 2016/12/15.
 */
public class AddArticleActivity extends BaseActivity implements AddArticleView, View.OnClickListener {
    private EditText mArticleTitle;
    private Spinner mArticleType;
    private RichEditor mRichEditor;
    private ImageView mInsertImage, mPostArticle;
    private AddArticlePresenter mPresenter;
    private Uri mImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_article);
        initView();
        setListeners();
    }

    private void initView() {
        mArticleTitle = (EditText) findViewById(R.id.article_title_editText);
        mArticleType = (Spinner) findViewById(R.id.article_type_spinner);
        mRichEditor = (RichEditor) findViewById(R.id.rich_editor);
        mInsertImage = (ImageView) findViewById(R.id.insert_image_imageView);
        mPostArticle = (ImageView) findViewById(R.id.post_article_imageView);

        mPresenter = new AddArticlePresenterImpl(this);
        mRichEditor.loadCSS("file:///android_asset/rich_editor.css");
        mRichEditor.setPlaceholder("在这写您的文章");
    }

    private void setListeners() {
        mInsertImage.setOnClickListener(this);
        mPostArticle.setOnClickListener(this);
    }

    @Override
    public void setArticleTitle(String title) {
        mArticleTitle.setText(title);
    }

    @Override
    public void setArticleType(String type) {

    }

    @Override
    public void insertImage(String imageUrl, String alt) {
        mRichEditor.insertImage(imageUrl, alt);
    }

    @Override
    public void setArticlContent(String content) {
        mRichEditor.setHtml(content);
    }

    @Override
    public void navigateToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.insert_image_imageView:
                showChooseImagePopwindow();
                //chooseImageFromAlbum();
                break;
            case R.id.take_photo_textView:
                takePhoto();
                break;
            case R.id.album_textView:
                chooseImageFromAlbum();
                break;
            case R.id.post_article_imageView:
                postArticle();
                break;
        }
    }

    private void showChooseImagePopwindow() {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_get_image_popwindow, null, false);
        TextView takePhoto = (TextView) view.findViewById(R.id.take_photo_textView);
        TextView album = (TextView) view.findViewById(R.id.album_textView);

        final PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setTouchable(true);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return false;
            }
        });
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        popupWindow.showAsDropDown(mInsertImage, -50, 50);
        takePhoto.setOnClickListener(this);
        album.setOnClickListener(this);
    }

    private void takePhoto() {
        //以系统时间作为该文件命名
        SimpleDateFormat formatter  =   new SimpleDateFormat("yyyy年MM月dd日HH-mm-ss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        //建立file文件用于保存来拍照后的图片
        File outputFile = new File(Environment.getExternalStorageDirectory(), str+".jpg");
        try {
            if (outputFile.exists()){
                outputFile.delete();
            }
            outputFile.createNewFile();

        }catch (Exception e){
            e.printStackTrace();
        }
        mImageUri = Uri.fromFile(outputFile);
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
        //启动相机程序
        startActivityForResult(intent, 12580);
    }

    private void chooseImageFromAlbum() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 12581);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            Uri imageUri = null;
            String imagePath = null;
            if (requestCode == 12581) {
                if (data == null) {
                    Toast.makeText(this, "选择图片文件出错", Toast.LENGTH_LONG).show();
                    return;
                }
                imageUri = data.getData();
                if (imageUri == null) {
                    Toast.makeText(this, "选择图片文件出错", Toast.LENGTH_LONG).show();
                    return;
                }
//                String[] pojo = {MediaStore.Images.Media.DATA};
//                //Cursor cursor = managedQuery(imageUri, pojo, null, null,null);
//                Cursor cursor = getContentResolver().query(imageUri, pojo, null, null, null);
//                if(cursor != null )
//                {
//                    cursor.moveToFirst();
//                    int columnIndex = cursor.getColumnIndexOrThrow(pojo[0]);
//                    imagePath = cursor.getString(columnIndex);
//                    Log.i("HZWING", "image path" + imagePath);
//                    mPresenter.uploadImage(imagePath);
//                    cursor.close();
//                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    imagePath = getImagePathKitKat(imageUri);
                } else {
                    imagePath = getImagePathBeforeKitKat(imageUri);
                }
                Log.i("HZWING", "image path" + imagePath);
                mPresenter.uploadImage(imagePath);
            } else if(requestCode == 12580) {
                Log.i("HZWING", mImageUri.getPath());
                mPresenter.uploadImage(mImageUri.getPath().toString());
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private String getImagePathKitKat(Uri uri) {
        String imagePath = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (DocumentsContract.isDocumentUri(this, uri)) {
                //如果是document类型的uri 则通过id进行解析处理
                String docId = DocumentsContract.getDocumentId(uri);
                if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                    //解析出数字格式id
                    String id = docId.split(":")[1];
                    String selection = MediaStore.Images.Media._ID + "=" + id;
                    imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
                } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                    Uri contentUri = ContentUris.withAppendedId(Uri.parse("" +
                            "content://downloads/public_downloads"), Long.valueOf(docId));
                    imagePath = getImagePath(contentUri, null);
                }
            } else if ("content".equals(uri.getScheme())) {
                //如果不是document类型的uri，则使用普通的方式处理
                imagePath = getImagePath(uri, null);
            }
            return imagePath;
        }
        return null;
    }

    private String getImagePathBeforeKitKat(Uri uri) {
        String imagePath = getImagePath(uri, null);
        return imagePath;
    }

    private String getImagePath(Uri uri, String seletion) {
        String path = null;
        Cursor cursor = getContentResolver().query(uri, null, seletion, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void postArticle() {
        String user = GanHuoCache.getAccount();
        SimpleDateFormat formatter  =   new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String curTime = formatter.format(curDate);
        String title = mArticleTitle.getText().toString();
        String type = mArticleType.getSelectedItem().toString();
        String image = mRichEditor.getHtml();
        int startIndex = image.indexOf("\"");
        int endIndex = image.indexOf("\"", startIndex+1);
        String imageUrl = image.substring(startIndex, endIndex+1);
        String content = Contants.PAGE_HEADER + Contants.PAGE_ARTICLE_TITLE_PRE + title + Contants.PAGE_ARTICLE_TITLE_POST +
                        Contants.PAGE_ARTICLE_ACCOUNT_PRE + user +" " + curTime + Contants.PAGE_ARTICLE_ACCOUNT_POST +
                        Contants.PAGE_ARTICLE_CONTENT_PRE + image + Contants.PAGE_ARTICLE_CONTENT_POST;
        mPresenter.postArticle(user, title, type, imageUrl, content);
    }
}
