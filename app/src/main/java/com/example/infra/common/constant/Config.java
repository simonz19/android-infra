package com.example.infra.common.constant;

/**
 * static config as per build environment
 */
public class Config {

    private static String url;

    public enum IpServerce {
        UAT("debug", "http://api.m.mtime.cn/"),
        RELEASE("release", "http://120.24.180.211/eduboss/MobileInterface/");
        public String env, url;

        private IpServerce(String env, String url) {
            this.env = env;
            this.url = url;
        }
    }

    public static void initNetWork(IpServerce type) {
        url = type.url;
    }

    public static String getUrl() {
        return url;
    }

}
