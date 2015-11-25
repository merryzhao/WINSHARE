/*
 * @(#)MiscResources.java
 *
 * Copyright 2012 Xinhua Online, Inc. All rights reserved.
 */
package com.winxuan.ec.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 服务器静态资源配置
 * 
 * @author Min
 * @version 1.0,Nov 23, 2012
 */
@Component("miscConfig")
public class MiscResources {

    private static final String ENV_RELEASE = "release";

    /**
     * 静态资源服务器地址
     */
    @Value("${front.misc.server}")
    private String server;

    /**
     * 环境配置 用于描述当前服务器环境
     * 
     * 现在只定义两个环境　为 dev 和 release
     * 
     */
    @Value("${front.misc.env}")
    private String env;

    /**
     * 发布版本一般为时间戳
     */
    @Value("${front.misc.version}")
    private String version;

    /**
     * JavaScript资源路径
     */
    @Value("${front.misc.js.path}")
    private String jsPath;

    /**
     * Css 资源路径
     */
    @Value("${front.misc.css.path}")
    private String cssPath;

    /**
     * JavaScript资源前缀 = server+jsPath
     */
    private String jsPrefix;

    /**
     * CSS 资源前缀 =server+cssPath
     */
    private String cssPrefix;

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getJsPath() {
        return jsPath;
    }

    public void setJsPath(String jsPath) {
        this.jsPath = jsPath;
    }

    public String getCssPath() {
        return cssPath;
    }

    public void setCssPath(String cssPath) {
        this.cssPath = cssPath;
    }

    public String getJsPrefix() {

        if (jsPrefix == null) {
            jsPrefix = this.server + jsPath;
        }

        return jsPrefix;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("MiscResources [server=");
        builder.append(server);
        builder.append(", env=");
        builder.append(env);
        builder.append(", version=");
        builder.append(version);
        builder.append(", jsPath=");
        builder.append(jsPath);
        builder.append(", cssPath=");
        builder.append(cssPath);
        builder.append(", jsPrefix=");
        builder.append(jsPrefix);
        builder.append(", cssPrefix=");
        builder.append(cssPrefix);
        builder.append("]");
        return builder.toString();
    }

    public String getCssPrefix() {

        if (cssPrefix == null) {
            cssPrefix = this.server + cssPath;
        }

        return cssPrefix;
    }

    public boolean isRelease() {

        return ENV_RELEASE.equalsIgnoreCase(env);

    }

}
