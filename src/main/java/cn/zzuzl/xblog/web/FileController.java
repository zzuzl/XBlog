package cn.zzuzl.xblog.web;

import cn.zzuzl.xblog.model.vo.Result;
import cn.zzuzl.xblog.model.UploadType;
import cn.zzuzl.xblog.service.FileService;
import cn.zzuzl.xblog.common.Common;
import cn.zzuzl.xblog.model.User;
import cn.zzuzl.xblog.service.UserService;
import cn.zzuzl.xblog.util.ConfigProperty;
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
    @Resource
    private ConfigProperty configProperty;

    /* 文件上传controller */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> upload(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put(Common.SUCCESS, true);
        if (!file.isEmpty()) {
            result = fileService.uploadPhoto(file);
        } else {
            result.put(Common.SUCCESS, false);
            result.put(Common.MSG, "文件不能为空");
        }
        return result;
    }

    /* 编辑器文件上传controller */
    @RequestMapping(value = "/uploadInArticle", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> uploadInArticle(@RequestParam("imgFile") MultipartFile imgFile,
                                               @RequestParam("dir") final String dir) throws IOException {
        UploadType uploadType = UploadType.valueOfType(dir);
        Map<String, Object> result = new HashMap<String, Object>();

        if (imgFile.isEmpty()) {
            result.put("error", 1);
            result.put("message", "请选择文件");
        } else {
            if (uploadType != null) {
                result = fileService.uploadFiles(imgFile, uploadType);
                /*if ((Integer) result.get("error") == 0 && uploadType == UploadType.IMAGE) {
                    // 对上传的图片进行缩放处理
                    zoomPicture(request.getSession().getServletContext().getRealPath("/") + result.get(Common.FILENAME));
                }*/
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
        if (user != null) {
            if (result.isSuccess()) {
                String path = request.getSession().getServletContext().getRealPath("/");
                String str = user.getUserId() + filename.substring(filename.lastIndexOf("."));
                File file = new File(path + filename);
                // 如果文件不存在，返回失败
                if (!file.exists()) {
                    result.setSuccess(false);
                    result.setMsg("文件不存在，请重新上传!");
                } else {
                    result = fileService.uploadToOSS(file, configProperty.getOssPhotoPic() + str);
                    if (result.isSuccess()) {
                        String newPath = result.getMsg();
                        result = userService.changePhoto(result.getMsg(), user.getUserId());
                        if (result.isSuccess()) {
                            user.setPhotoSrc(newPath);
                            request.getSession().setAttribute(Common.USER, user);
                        }
                    }
                }
            }
        } else {
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
