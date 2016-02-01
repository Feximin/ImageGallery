package com.feximin.imagegallery;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.ObjectAnimator;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoView;

public class MainActivity extends AppCompatActivity {

    private ViewPager mPager;
    private AdapterGallery mAdapter;
    private RelativeLayout mRlTitle;
    private TextView mTxtTitle;
    private int mTitleHeight;
    private ImageButton mButBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRlTitle = (RelativeLayout) findViewById(R.id.rl_top);
        mTxtTitle = (TextView) findViewById(R.id.txt_title);
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        mTitleHeight = (int) (48 * metrics.density);
        mButBack = (ImageButton) findViewById(R.id.bug_back);
        mButBack.setOnClickListener(v -> finish());
        mPager = (ViewPager) findViewById(R.id.pager);

        mPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                mTxtTitle.setText(String.format("%s/%s", position + 1, mAdapter.getCount()));
            }
        });
        mAdapter = new AdapterGallery(this);
        mPager.setAdapter(mAdapter);
        List<String> data = new ArrayList<>();
        data.add("http://p4.so.qhimg.com/t01472db906ce126ebe.jpg");
        data.add("http://p2.so.qhimg.com/t015c249f917fd0395c.jpg");
        data.add("http://p4.so.qhimg.com/t01c48c2b155f23f1de.jpg");
        data.add("http://p2.so.qhimg.com/t01967e6292f2689c37.jpg");
        data.add("http://p2.so.qhimg.com/t018e7e64110afdd0ec.jpg");
        data.add("http://p3.so.qhimg.com/t0176c15c7fd7cac5c8.jpg");
        data.add("http://p2.so.qhimg.com/t017ee16d1d499b6022.jpg");
        data.add("http://p0.so.qhimg.com/t016f304e739ee2cc15.jpg");
        data.add("http://p4.so.qhimg.com/t01b087f06be900ac79.jpg");
        mAdapter.add(data);
    }

    private static final int GONE = 0;
    private static final int VISIBLE = 1;
    private static final int ACTIVE = 2;
    @IntDef({GONE, VISIBLE, ACTIVE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Status{}

    private int mCurStatus = VISIBLE;
    private void toggleTitle(){
        if(mCurStatus == ACTIVE) return;
        ObjectAnimator animator;
        boolean isToShow = true;
        if(mCurStatus == VISIBLE){
            isToShow = false;
            animator = ObjectAnimator.ofFloat(mRlTitle, "translationY", 0, -mTitleHeight).setDuration(200);
        }else{
            mRlTitle.setVisibility(View.VISIBLE);
            animator = ObjectAnimator.ofFloat(mRlTitle, "translationY", -mTitleHeight, 0).setDuration(200);
        }
        mCurStatus = ACTIVE;
        final boolean finalIsToShow = isToShow;
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if(finalIsToShow){
                    mCurStatus = VISIBLE;
                }else{
                    mRlTitle.setVisibility(View.GONE);
                    mCurStatus = GONE;
                }
            }
        });
        animator.start();
    }

    class AdapterGallery extends PagerAdapter{
        private List<String> mData = new ArrayList<>();
        private Activity mActivity;
        public AdapterGallery(Activity activity){
            mActivity = activity;
        }

        public void add(String path){
            mData.add(path);
            notifyDataSetChanged();
        }

        public void add(List<String> list){
            mData.addAll(list);
            notifyDataSetChanged();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            PhotoView  photoView = new PhotoView(mActivity);
            Glide.with(mActivity).load(mData.get(position)).crossFade().into(photoView);
            container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            photoView.setOnViewTapListener((v, x, y) -> toggleTitle());
            return photoView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

}
