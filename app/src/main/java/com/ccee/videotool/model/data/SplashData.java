package com.ccee.videotool.model.data;

import android.databinding.BaseObservable;
import android.databinding.ObservableField;

public class SplashData extends BaseObservable {
    public ObservableField<String> countDown = new ObservableField<>();
    public ObservableField<String> imgUrl = new ObservableField<>();
}
