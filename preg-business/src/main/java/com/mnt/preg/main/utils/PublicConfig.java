
package com.mnt.preg.main.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 公共配置参数
 * 
 * @author zy
 * @version 1.0
 * 
 *          变更履历： v1.0 2014-12-15 zy 初版
 */
@Component(value = "config-web")
public class PublicConfig {

    /** resource资源服务器路径 */
    @Value("${resource.server.path}")
    public String resourceServerPath;

    /** resource资源绝对路径 */
    @Value("${resource.absolute.path}")
    public String resourceAbsolutePath;

    public String getResourceServerPath() {
        return resourceServerPath;
    }

    public void setResourceServerPath(String resourceServerPath) {
        this.resourceServerPath = resourceServerPath;
    }

    public String getResourceAbsolutePath() {
        return resourceAbsolutePath;
    }

    public void setResourceAbsolutePath(String resourceAbsolutePath) {
        this.resourceAbsolutePath = resourceAbsolutePath;
    }

}
