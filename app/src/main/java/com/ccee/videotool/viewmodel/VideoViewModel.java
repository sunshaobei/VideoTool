package com.ccee.videotool.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.ccee.videotool.constants.CCEEConstants;
import com.ccee.videotool.model.data.VideoData;
import com.ccee.videotool.model.db.DBVideo;
import com.sunsh.baselibrary.utils.BitmapUtils;

import java.io.File;

import io.reactivex.disposables.CompositeDisposable;

public class VideoViewModel extends AndroidViewModel {
    private final MutableLiveData<VideoData> videoDataMutableLiveData;
    private LiveData<VideoData> videoDataLiveData;
    private static final MutableLiveData ABSENT = new MutableLiveData();

    {
        //noinspection unchecked
        ABSENT.setValue(null);
    }

    private final CompositeDisposable mDisposable = new CompositeDisposable();

    public VideoViewModel(@NonNull Application application) {
        super(application);
        videoDataMutableLiveData = new MutableLiveData<>();
        videoDataLiveData = videoDataMutableLiveData;
    }

    public void load4EditData(DBVideo dbVideo) {
        VideoData videoData = new VideoData();
        videoData.setBottomLeft("删除");
        videoData.setBottomRight("提交");
        videoData.setTitle(dbVideo.getTitle());
        videoData.setCover(dbVideo.getCover());
        videoData.setAliVideoId(dbVideo.getAliVideoId());
        videoData.setLocalPath(dbVideo.getLocalPath());
        videoData.setDes(dbVideo.getDescription());
        videoData.setArticle(dbVideo.getProductTitle());
        videoData.setCategory(dbVideo.getCategoryTitle());
        videoData.setShow(dbVideo.getVideoId() == null);
        videoDataMutableLiveData.setValue(videoData);
    }

    public void load4EditDraft(DBVideo dbVideo) {
        VideoData videoData = new VideoData();
        videoData.setBottomLeft("删除");
        videoData.setBottomRight("提交");
        videoData.setTitle(dbVideo.getTitle());
        String localImg = dbVideo.getLocalImg();
        File file = new File(localImg);
        if (!file.exists())
            BitmapUtils.fetchVideoThum(CCEEConstants.PATH, dbVideo.getLocalPath());
        videoData.setCover(localImg);
        videoData.setAliVideoId(dbVideo.getAliVideoId());
        videoData.setLocalPath(dbVideo.getLocalPath());
        videoData.setDes(dbVideo.getDescription());
        videoData.setArticle(dbVideo.getProductTitle());
        videoData.setCategory(dbVideo.getCategoryTitle());
        videoData.setShow(dbVideo.getVideoId() == null);
        videoDataMutableLiveData.setValue(videoData);
    }


    public void load4UpLoadData(DBVideo dbVideo) {
        VideoData videoData = new VideoData();
        videoData.setLocalPath(dbVideo.getLocalPath());
        String localImg = dbVideo.getLocalImg();
        File file = new File(localImg);
        if (!file.exists())
            BitmapUtils.fetchVideoThum(CCEEConstants.PATH, dbVideo.getLocalPath());
        videoData.setCover(localImg);
        videoData.setBottomLeft("删除");
        videoData.setBottomRight("提交");
        videoData.setShow(true);
        videoDataMutableLiveData.setValue(videoData);
    }

    /**
     * LiveData支持了lifecycle生命周期检测
     *
     * @return
     */
    public LiveData<VideoData> getVideoDataLiveData() {
        return videoDataLiveData;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mDisposable.clear();
    }
}
