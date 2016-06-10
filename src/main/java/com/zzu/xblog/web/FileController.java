package com.zzu.xblog.web;

import com.zzu.xblog.common.Common;
import com.zzu.xblog.model.UploadType;
import com.zzu.xblog.service.FileService;
import com.zzu.xblog.service.UserService;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

import static com.zzu.xblog.model.UploadType.*;

/**
 * 文件相关的controller
 */
@Controller
@RequestMapping("file")
public class FileController {
    @Resource
    private FileService fileService;
    @Resource
    private UserService userService;

    /* 文件上传controller */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject upload(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {
        JSONObject result = new JSONObject();
        result.put(Common.SUCCESS, true);
        if (!file.isEmpty()) {
            result = fileService.uploadPhoto(file, request);
        } else {
            result.put(Common.SUCCESS, false);
            result.put(Common.MSG, "文件不能为空");
        }
        return result;
    }

    /* 发表文章文件上传controller */
    @RequestMapping(value = "/uploadInArticle", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject uploadInArticle(@RequestParam("imgFile") MultipartFile imgFile,
                                      @RequestParam("dir") final String dir,
                                      HttpServletRequest request) throws IOException {
        UploadType uploadType = null;
        if (dir.equals(FILE.getType())) {
            uploadType = FILE;
        } else if (dir.equals(FLASH.getType())) {
            uploadType = FLASH;
        } else if (dir.equals(MEDIA.getType())) {
            uploadType = MEDIA;
        } else if (dir.equals(IMAGE.getType())) {
            uploadType = IMAGE;
        }
        JSONObject result = new JSONObject();

        if (imgFile.isEmpty()) {
            result.put("error", 1);
            result.put("message", "请选择文件");
        } else {
            if (uploadType != null) {
                result = fileService.uploadFiles(imgFile, uploadType, request);
            } else {
                result.put("error", 1);
                result.put("message", "文件类型错误");
            }
        }

        return result;
    }

    /* 文件裁剪与缩放,并设置头像 */
    @RequestMapping(value = "/changePhoto", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject changePhoto(@RequestParam("filename") String filename, HttpServletRequest request,
                                  Double x, Double y, Double width, Double height) {
        JSONObject result = fileService.cropper(filename, x, y, width, height, request);
        if (result.getBoolean(Common.SUCCESS)) {
            int userId = 1;
            result = userService.changePhoto(filename, userId);
        }
        return result;
    }
}
