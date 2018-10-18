/**
 * 
 */

package com.mnt.preg.web.form;

/**
 * @作者 zy
 * @版本 1.0.0
 * @创建日期 2014-4-23 下午03:12:24
 * @修改者
 * @修改日期
 */
public class FileUploadForm {

    private boolean result;// 上传结果

    private String uppath;// 上传路径

    public FileUploadForm() {
    }

    public FileUploadForm(boolean result) {
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

}
