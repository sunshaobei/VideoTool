package com.ccee.videotool.arouter;

public interface RoutePath {
    @LoginIntercept
    String MAIN = "/app/main";
    String LOGIN = "/app/login";
    String SYS_SETTING = "/app/sys_setting";
    String AUDIT_NOTICE = "/app/audit/notice" ;
}
