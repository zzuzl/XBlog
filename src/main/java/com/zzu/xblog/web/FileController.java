package com.zzu.xblog.web;

import com.zzu.xblog.common.Common;
import com.zzu.xblog.dto.Result;
import com.zzu.xblog.model.UploadType;
import com.zzu.xblog.model.User;
import com.zzu.xblog.service.FileService;
import com.zzu.xblog.service.UserService;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> upload(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {
        Map<String,Object> result = new HashMap<String, Object>();
        result.put(Common.SUCCESS, true);
        if (!file.isEmpty()) {
            result = fileService.uploadPhoto(file, request);
        } else {
            result.put(Common.SUCCESS, false);
            result.put(Common.MSG, "文件不能为空");
        }
        return result;
    }

    /* 编辑器文件上传controller */
    @RequestMapping(value = "/uploadInArticle", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> uploadInArticle(@RequestParam("imgFile") MultipartFile imgFile,
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
        Map<String,Object> result = new HashMap<String, Object>();

        if (imgFile.isEmpty()) {
            result.put("error", 1);
            result.put("message", "请选择文件");
        } else {
            if (uploadType != null) {
                result = fileService.uploadFiles(imgFile, uploadType, request);
                if ((Integer)result.get("error") == 0 && uploadType == UploadType.IMAGE) {
                    // 对上传的图片进行缩放处理
                    zoomPicture(request.getSession().getServletContext().getRealPath("/") + result.get(Common.FILENAME));
                }
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
    public Result changePhoto(@RequestParam("filename") String filename, HttpServletRequest request,
                              Double x, Double y, Double width, Double height) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Common.USER);
        Result result = fileService.cropper(filename, x, y, width, height, request);
        if (user == null) {
            result.setSuccess(false);
            result.setMsg("用户未登录");
        }

        return result;
    }

    /**
     * 缩放图片
     *
     * @param fileName
     */
    private void zoomPicture(final String fileName) {
        int width = 0, height = 0;
        try {
            FileInputStream fis = new FileInputStream(fileName);
            BufferedImage bufferedImage = ImageIO.read(fis);
            width = bufferedImage.getWidth();
            height = bufferedImage.getHeight();

            if (width > 0 && height > 0) {
                if (width > Common.MAX_PICTURE_WIDTH) {
                    height = (int) (((width + 0.0) / height) * Common.MAX_PICTURE_WIDTH);
                    width = Common.MAX_PICTURE_WIDTH;
                }

                Thumbnails.of(fileName)
                        .size(width, height)
                        .toFile(new File(fileName));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
