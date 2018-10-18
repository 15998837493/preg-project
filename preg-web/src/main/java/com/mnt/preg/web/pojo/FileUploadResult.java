/**
 * 
 */

package com.mnt.preg.web.pojo;

/**
 * @作者 zy
 * @版本 1.0.0
 * @创建日期 2014-4-23 下午03:12:24
 * @修改者
 * @修改日期
 */
public class FileUploadResult {

    private boolean result;// 上传结果

    private String message;// 结果信息

    private String uppath;// 上传路径

    public FileUploadResult() {
    }

    public FileUploadResult(boolean result) {
        this.result = result;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getUppath() {
        return uppath;
    }

    public void setUppath(String uppath) {
        this.uppath = uppath;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
