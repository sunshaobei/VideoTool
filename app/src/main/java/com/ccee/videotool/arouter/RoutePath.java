package com.ccee.videotool.arouter;

public interface RoutePath {
    @LoginIntercept
    String MAIN = "/app/main";
    String LOGIN = "/app/login";
    String SYS_SETTING = "/app/sys_setting";
    String AUDIT_NOTICE = "/app/audit/notice" ;
    String VIDEO_DETAIL = "/app/video/detail";
    String VIDEO_EDIT = "/app/video/edit";
    String VIDEO_UPLOAD = "/app/video/upload";
    String ARTICLES_CHOOSE ="/app/articles/choose";
    String VIDEO_PLAY = "/app/video/play";
    String USER_AGREEMENT = "/app/agreement";
}
