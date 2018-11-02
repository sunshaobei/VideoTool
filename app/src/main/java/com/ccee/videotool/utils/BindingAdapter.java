package com.ccee.videotool.utils;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ccee.videotool.R;

import java.io.File;

public class BindingAdapter {
    @android.databinding.BindingAdapter("imgUrl")
    public static void imagUrl(ImageView v, String imgUrl) {
        Glide.with(v.getContext()).load(imgUrl).placeholder(R.mipmap.ic_default).error(R.mipmap.ic_default).diskCacheStrategy(DiskCacheStrategy.RESULT).into(v);
    }

    @android.databinding.BindingAdapter("img")
    public static void img(ImageView v, String imgUrl) {
        Glide.with(v.getContext()).load(imgUrl).diskCacheStrategy(DiskCacheStrategy.RESULT).into(v);
    }
}
