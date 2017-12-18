package com.pingia.cn.myapplication;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView contentTv;
    ImageView expandIv;

    int expandHeight;  //  textview 展开的高度
    int contentFoldHeight;  //textview 折叠的高度

    private int mFoldLines = 4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contentTv = findViewById(R.id.tv_shortdetail);
        expandIv = findViewById(R.id.expand_iv);

        contentTv.setOnClickListener(this);
        expandIv.setOnClickListener(this);

        initData();
    }

    private void initData(){
        contentTv.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                contentTv.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                int lineCount = contentTv.getLineCount();

                expandHeight = contentTv.getHeight();

                if(lineCount > mFoldLines){
                    contentTv.setMaxLines(mFoldLines);
                    contentTv.setEllipsize(TextUtils.TruncateAt.END);
                    expandIv.setVisibility(View.VISIBLE);
                }else{
                    expandIv.setVisibility(View.GONE);
                }

            }
        });

        result("我的爱国家我的爱国家我的爱国家我的爱国家我的爱国家我的爱国家我的爱国家我的爱国家我的爱国家我的爱国家" +
                "我的爱国家我的爱国家我的爱国家我的爱国家我的爱国家我的爱国家我的爱国家我的爱国家我的爱国家我的爱国家我的爱国家我的爱国家"+
                "我的爱国家我的爱国家我的爱国家我的爱国家我的爱国家我的爱国家我的爱国家我的爱国家我的爱国家我的爱国家我的爱国家我的爱国家"+
                "我的爱国家我的爱国家我的爱国家我的爱国家我的爱国家我的爱国家我的爱国家我的爱国家我的爱国家我的爱国家我的爱国家我的爱国家"+
                "我的爱国家我的爱国家我的爱国家我的爱国家我的爱国家我的爱国家我的爱国家我的爱国家我的爱国家我的爱国家我的爱国家我的爱国家"+
                "我的爱国家我的爱国家我的爱国家我的爱国家我的爱国家我的爱国家我的爱国家我的爱国家我的爱国家我的爱国家我的爱国家我的爱国家"+
                "我的爱国家我的爱国家我的爱国家我的爱国家我的爱国家我的爱国家我的爱国家我的爱国家我的爱国家我的爱国家我的爱国家我的爱国家"+
                "我的爱国家我的爱国家我的爱国家我的爱国家我的爱国家我的爱国家我的爱国家我的爱国家我的爱国家我的爱国家我的爱国家我的爱国家"+
                "我的爱国家我的爱国家我的爱国家我的爱国家我的爱国家我的爱国家我的爱国家我的爱国家我的爱国家我的爱国家我的爱国家我的爱国家"+
                "我的爱国家我的爱国家我的爱国家我的爱国家我的爱国家我的爱国家我的爱国家我的爱国家我的爱国家我的爱国家我的爱国家我的爱国家");
    }

    public void setFoldLines(int lines){
        this.mFoldLines = lines;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_shortdetail:
                doExpandOrFoldContent(mExpanded = !mExpanded);
                break;
            case R.id.expand_iv:
                doExpandOrFoldContent(mExpanded = !mExpanded);
                break;
        }
    }

    private void result(String text){
        contentTv.setText(text);
    }



    private void doExpandOrFoldContent(boolean isExpanding) {

        if(!isExpanding){

            expandIv.animate().rotationBy(-180).setDuration(1000).start();  //逆时针

            final int height = contentTv.getHeight();
            ObjectAnimator animator = ObjectAnimator.ofInt(new WrapperView(contentTv),"height", height, contentFoldHeight).setDuration(1000);
            animator .addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    expandIv.setEnabled(false);
                    expandIv.setClickable(false);

                    contentTv.setEnabled(false);
                    contentTv.setClickable(false);
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    contentTv.setMaxLines(4);
                    expandIv.setEnabled(true);
                    expandIv.setClickable(true);
                    contentTv.setEnabled(true);
                    contentTv.setClickable(true);
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });

            animator.start();


        }else{
            expandIv.animate().rotationBy(180).setDuration(1000).start();       //顺时针转180；

            final int height = contentTv.getHeight();
            contentFoldHeight = height;
            contentTv.setMaxLines(Integer.MAX_VALUE);
            ObjectAnimator animator = ObjectAnimator.ofInt(new WrapperView(contentTv),"height", height, expandHeight).setDuration(1000);
            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    expandIv.setEnabled(false);
                    expandIv.setClickable(false);
                    contentTv.setEnabled(false);
                    contentTv.setClickable(false);
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    expandIv.setEnabled(true);
                    expandIv.setClickable(true);
                    contentTv.setEnabled(true);
                    contentTv.setClickable(true);
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });

            animator.start();
        }


    }

    private class WrapperView{
        private View mView;
        public WrapperView(View view){
            this.mView = view;
        }
        public void setHeight(int height){
            this.mView.getLayoutParams().height = height;
            mView.requestLayout();
        }

        public void setWidth(int width){
            this.mView.getLayoutParams().width = width;
            mView.requestLayout();
        }
    }

    private boolean mExpanded = false;
}
