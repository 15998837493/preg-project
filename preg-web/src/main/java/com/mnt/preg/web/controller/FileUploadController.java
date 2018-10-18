
package com.mnt.preg.web.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.mnt.preg.web.constants.PathConstant;
import com.mnt.preg.web.form.UploadForm;
import com.mnt.preg.web.mapping.LoginMapping;
import com.mnt.preg.web.pojo.FileUploadResult;

/**
 * 上传文件
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2015-3-25 zcq 初版
 */
@Controller
public class FileUploadController extends BaseController {

    /**
     * 通用上传
     * 
     * @author zcq
     * @param request
     * @param response
     * @param uploadForm
     * @return
     */
    @RequestMapping(value = LoginMapping.UPLOAD)
    public void uploadfive(HttpServletResponse response, UploadForm uploadForm) {
        // 图片上传返回结果
        FileUploadResult result = new FileUploadResult();
        MultipartFile patch = uploadForm.getFiledata();
        if (patch != null && !patch.isEmpty()) {
            String uploadType = uploadForm.getPicType();
            // 临时文件绝对路径
            String tmp_path = publicConfig.getResourceAbsolutePath() + PathConstant.template_image_temp;
            // 临时文件相对路径
            String result_path = publicConfig.getResourceServerPath() + PathConstant.template_image_temp;
            // 源文件名称
            String fileName = patch.getOriginalFilename();
            // 取扩展名
            String suffix = fileName.substring(fileName.lastIndexOf("."));
            // 创建临时目录
            new File(tmp_path).mkdirs();
            // 定义文件名称
            String newFileName = "";
            if ("synchro".equals(uploadType)) {
                tmp_path = publicConfig.getResourceAbsolutePath() + "resource/core_db/";
                newFileName = fileName;
            } else {
                newFileName = UUID.randomUUID().toString() + suffix;
            }
            // 保存到临时路径下
            try {
                patch.transferTo(new File(tmp_path + newFileName));
                // 设置返回结果
                result.setResult(true);
                if (tmp_path.endsWith(File.separator) || tmp_path.endsWith("/")) {
                    result.setUppath(result_path + newFileName);
                } else {
                    result.setUppath(result_path + File.separator + newFileName);
                }
                if ("synchro".equals(uploadType)) {
                    // this.getCoreWebService().executeReadSQL(tmp_path + newFileName);//TODO:之前同步数据用
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (File.separator.equals("\\")) {
                result.setUppath(result.getUppath().replaceAll("\\\\", "/"));
            }
        }
        PrintWriter out = null;
        try {
            response.setHeader("Content-type", "text/html;charset=UTF-8");
            out = response.getWriter();
            out.write(result.getUppath());
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
