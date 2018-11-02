package com.ccee.videotool.model.http;

public interface Api {
    int VERSION = 10000;
    String TEST_HOST = "http://test.api.ccee.com/api/app/";
    String RELEASSE_HOST = "http://test.api.ccee.com/api/app/";
    String HOST = TEST_HOST;

    String UPLOAD_VIDEO_STS = "http://172.16.15.87:9001/UploadVideoSTS";

    /**
     * {@link com.ccee.videotool.model.entities.request.LoginRequest#}
     */
    String LOGIN = HOST + "login";


    /**
     * {@link com.ccee.videotool.model.entities.request.VideoListRequest#}
     */
    String VIDEO_LIST = HOST + "userVideoList";

    /**
     * {@link com.ccee.videotool.model.entities.request.VideoDetailRequest#}
     */
    String VIDEO_DETAIL = HOST + "videoDetail";

    /**
     * {@link com.ccee.videotool.model.entities.request.AllCategoryRequest#}
     */
    String ALL_CATEGORY = HOST + "allCategory";
    /**
     * {@link com.ccee.videotool.model.entities.request.ArticleListRequest#}
     */
    String ARTICLE_LIST = HOST + "userProductList";

    /**
     * {@link com.ccee.videotool.model.entities.request.AddVideoRequest#}
     */
    String ADD_VIDEO = HOST + "addVideo";

    /**
     * {@link com.ccee.videotool.model.entities.request.EditVideoRequest#}
     */
    String EDIT_VIDEO = HOST + "updateVideo";
    /**
     */
    String UPLOAD_COVER = HOST + "coverUpload";

    /**
     * {@link com.ccee.videotool.model.entities.request.BaseConfigRequest#}
     */
    String CONFIG = HOST + "config";

    String VIDEO_DELETE = HOST + "deleteVideo";
}
