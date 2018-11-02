package com.ccee.videotool.model.http;

import com.ccee.videotool.model.entities.response.VideoSTSBean;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UpLoadService {
    @POST("{url}")
    Observable<VideoSTSBean> getUpLoadVideoSTS(@Path("url") String url);

}
