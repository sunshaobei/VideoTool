package com.sunsh.baselibrary.base.dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.sunsh.baselibrary.R;
import com.sunsh.baselibrary.utils.StatusBarUtil;
import com.sunsh.baselibrary.widgets.swipeback.StackManager;

public abstract class BaseDialogFragment extends DialogFragment implements View.OnTouchListener {

    protected final FrameLayout rootView;
    protected final View conentView;
    private boolean cancelAble;

    @SuppressLint("ClickableViewAccessibility")
    public BaseDialogFragment() {
        rootView = new FrameLayout(StackManager.getInstance().getCurrentActivity());
        rootView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        conentView = LayoutInflater.from(StackManager.getInstance().getCurrentActivity()).inflate(getLayoutId(), rootView,false);
        rootView.addView(conentView);
        rootView.setOnTouchListener(this);
        initViews();
    }


    public void setCanceledOnTouchOutside(boolean cancelAble) {
        this.cancelAble = cancelAble;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        View view = getView();
        if (view != null) {
            if (view.getParent() != null) {
                ((ViewGroup)view.getParent()).removeView(view);
            }
        }

        super.onActivityCreated(savedInstanceState);
        Window window = getDialog().getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
        StatusBarUtil.darkMode(window, Color.TRANSPARENT, 0.2f, true);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setGravity(Gravity.CENTER);
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);
        // 设置显示动画
        if (getWindowAnimation() != 0)
            window.setWindowAnimations(getWindowAnimation());
    }


    @Override
    public void show(FragmentManager manager, String tag) {
        if (!isAdded())
            super.show(manager, tag);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, R.style.dialog_fragment);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return rootView;
    }


    public void show(FragmentManager manager) {
        if (!this.isAdded())
            show(manager, getClass().getSimpleName());
    }

    public <T extends View> T findViewById(int id) {
        return rootView.findViewById(id);
    }

    protected abstract void initViews();

    public abstract int getLayoutId();

    public int getWindowAnimation() {
        return 0;
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        float x = event.getRawX();
        float y = event.getRawY();
        Rect rect = new Rect();
        conentView.getGlobalVisibleRect(rect);
        if ((x < rect.left || x > rect.right) || (y < rect.top || y > rect.bottom)) {
            if (cancelAble)
                dismiss();
        }
        return false;
    }
}
