package com.ccee.videotool.arouter;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;

public class SchemeFilterActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Uri uri = getIntent().getData();
    }
}
