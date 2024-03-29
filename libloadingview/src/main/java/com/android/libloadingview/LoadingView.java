package com.android.libloadingview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.IntDef;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.Group;
import com.cunoraz.gifview.library.GifView;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author kaixinli
 * @description: 我挥舞着键盘和本子，发誓要把世界写个明明白白。
 * @date : 2019/9/25 15:05
 */
public class LoadingView extends ConstraintLayout {

    public static final int LOADING_MODE_TRANSPARENT_BG = 1;//背景透明
    public static final int LOADING_MODE_WHITE_BG = 2;//背景不透明

    private RetryCallBack mRetryCallBack;
    private OnCancelListener mCloseListener;
    private ViewGroup mParentViewGroup;
    private LinearLayout mLoadingLayout;
    private Group mErrorGroup;
    private GifView mGifView;
    private TextView mTvErrorMsg;
    private Button mBtnRetry;
    private int mCurMode;
    private boolean isCancelAble;

    public void setCloseListener(OnCancelListener closeListener) {
        this.mCloseListener = mCloseListener;
    }

    @IntDef({LOADING_MODE_TRANSPARENT_BG,LOADING_MODE_WHITE_BG})
    @Retention(RetentionPolicy.SOURCE)
    public @interface LoadingMode{};

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }

    public LoadingView(Context context) {
        super(context);
    }

    public LoadingView(Context context, AttributeSet attrs) {
        super(context, attrs,0);
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mLoadingLayout = findViewById(R.id.loading_load_layout);
        mErrorGroup = findViewById(R.id.loading_error_layout);
        mBtnRetry = findViewById(R.id.loading_btn_retry);
        mGifView = findViewById(R.id.loading_gif_view);
        mTvErrorMsg = findViewById(R.id.loading_tv_error_msg);

        mBtnRetry.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mRetryCallBack != null){
                    mRetryCallBack.onRetry();
                    if(mCurMode == LOADING_MODE_WHITE_BG){
                        show(mCurMode);
                    }
                }
            }
        });
    }

    public static LoadingView inject(Context context,ViewGroup group){
        for (int i = 0; i < group.getChildCount(); i++) {
            if(group.getChildAt(i) instanceof LoadingView){
                return (LoadingView)group.getChildAt(i);
            }
        }
        LoadingView loadingView = (LoadingView) LayoutInflater.from(context).inflate(R.layout.layout_loading,null);
        loadingView.mParentViewGroup = loadingView;

        return loadingView;
    }

    public void show(@LoadingMode int mode) {
        show(mode,false);
    }

    @SuppressLint("ResourceType")
    public void show(@LoadingMode int mode, boolean cancelAble) {
        isCancelAble = cancelAble;

        if(this.getParent() == null){
            if(mParentViewGroup instanceof FrameLayout || mParentViewGroup instanceof RelativeLayout){
                mParentViewGroup.addView(this);
            }else if(mParentViewGroup instanceof ConstraintLayout){
                ConstraintSet constraintSet = new ConstraintSet();
                this.setId(100000);
                constraintSet.clone((ConstraintLayout) mParentViewGroup);
                constraintSet.connect(this.getId(),ConstraintSet.TOP,ConstraintSet.PARENT_ID,ConstraintSet.TOP);
                constraintSet.connect(this.getId(),ConstraintSet.LEFT,ConstraintSet.PARENT_ID,ConstraintSet.LEFT);
                constraintSet.connect(this.getId(),ConstraintSet.RIGHT,ConstraintSet.PARENT_ID,ConstraintSet.RIGHT);
                constraintSet.connect(this.getId(),ConstraintSet.BOTTOM,ConstraintSet.PARENT_ID,ConstraintSet.BOTTOM);

                constraintSet.constrainWidth(this.getId(),ConstraintSet.MATCH_CONSTRAINT);
                constraintSet.constrainHeight(this.getId(),ConstraintSet.MATCH_CONSTRAINT);

                mParentViewGroup.addView(this);
                constraintSet.applyTo((ConstraintLayout) mParentViewGroup);
            }else{
                ViewGroup parent = (ViewGroup) mParentViewGroup.getParent();
                FrameLayout root = new FrameLayout(parent.getContext());
                root.setLayoutParams(mParentViewGroup.getLayoutParams());
                parent.removeView(mParentViewGroup);
                root.addView(mParentViewGroup,new ViewGroup.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,FrameLayout.LayoutParams.MATCH_PARENT));
                root.addView(this);
                parent.addView(root);
            }
        }

        mCurMode = mode;
        if(mode == LOADING_MODE_WHITE_BG){
            setBackgroundColor(Color.WHITE);
        }else{
            setBackgroundColor(Color.TRANSPARENT);
        }

        mErrorGroup.setVisibility(GONE);
        mLoadingLayout.setVisibility(VISIBLE);
        //setFocusable这个是用键盘是否能获得焦点
        //setFocusableInTouchMode这个是触摸是否能获得焦点
        setFocusableInTouchMode(true);
        //获取焦点
        requestFocus();
        //在执行的时候，发现每次都会响应两次事件，原来是每次按下及松开时都会执行一次
        setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //如果抬起或者按了返回键
                if(event.getAction() == KeyEvent.ACTION_UP || keyCode == KeyEvent.KEYCODE_BACK){
                    //要判断用户能否看见某个view，应使用isShown()
                    if(isShown()){
                        if(isCancelAble && mCurMode == LOADING_MODE_TRANSPARENT_BG){
                            close();
                        }
                        return true;
                    }
                }
                return false;
            }
        });
        play();
    }

    //这里播放帧动画或者GIF动画
    private void play() {
        mGifView.play();
    }

    public void close() {
        mGifView.pause();
        ViewGroup parent = (ViewGroup) getParent();
        if(parent != null){
            parent.removeView(this);
        }
    }

    public void onError(String msg,RetryCallBack callBack){
        if(mCurMode == LOADING_MODE_TRANSPARENT_BG){
            Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
            close();
            return;
        }

        mLoadingLayout.setVisibility(GONE);
        mErrorGroup.setVisibility(VISIBLE);
        mTvErrorMsg.setText(msg);
        mRetryCallBack = callBack;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if(mCloseListener != null){
            mCloseListener.onCancel();
        }
    }

    public boolean isShow(){
        return getParent() != null;
    }

    public interface  RetryCallBack{
        void onRetry();
    }

    public interface OnCancelListener{
        void onCancel();
    }


}
