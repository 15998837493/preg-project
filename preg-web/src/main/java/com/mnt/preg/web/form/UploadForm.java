/**
 * 
 */

package com.mnt.preg.web.form;

import org.springframework.web.multipart.MultipartFile;

/**
 * 上传Form表单
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：v1.0 2015-2-4 zcq 初版
 */
public class UploadForm {

    /** 上传事件ID，主要区分属于哪部分业务使用 */
    private String upid;

    /** 当上传多个图片，此参数有用，代表第几张图片 */
    private String picsheet;

    /** 文件列表 */
    private MultipartFile Filedata;

    /** 临时上传路径 */
    private String picType;

    /** 文件名验证表达式 */
    private String regexp;

    public String getUpid() {
        return upid;
    }

    public void setUpid(String upid) {
        this.upid = upid;
    }

    public String getPicsheet() {
        return picsheet;
    }

    public void setPicsheet(String picsheet) {
        this.picsheet = picsheet;
    }

    public MultipartFile getFiledata() {
        return Filedata;
    }

    public void setFiledata(MultipartFile filedata) {
        Filedata = filedata;
    }

    public String getPicType() {
        return picType;
    }

    public void setPicType(String picType) {
        this.picType = picType;
    }

    public String getRegexp() {
        return regexp;
    }

    public void setRegexp(String regexp) {
        this.regexp = regexp;
    }

}
