package cn.zzuzl.xblog.service;

import cn.zzuzl.xblog.common.Common;
import cn.zzuzl.xblog.model.vo.Result;
import cn.zzuzl.xblog.model.UploadType;
import cn.zzuzl.xblog.util.ConfigProperty;
import cn.zzuzl.xblog.util.FileUtil;
import cn.zzuzl.xblog.util.Utils;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.PutObjectRequest;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 文件相关service
 */
@Service
public class FileService {
    private final Logger logger = LogManager.getLogger(getClass());
    private static final String endpoint = "http://oss-cn-shanghai.aliyuncs.com";
    private static final String accessKeyId = "LTAIt4St90z5n4ZK";
    private static final String accessKeySecret = "hEm0M3TDbqmZv77Vvhxwx2qU5GReM6";
    private static final String bucketName = "xblog-mis";

    /**
     * 上传头像图片
     *
     * @param file
     * @return
     */
    public Map<String, Object> uploadPhoto(MultipartFile file) {

        Map<String, Object> result = new HashMap<String, Object>();
        result.put(Common.SUCCESS, false);
        if (file.getSize() > 1024 * 1024) {
            result.put(Common.MSG, "文件过大：不超过1MB");
            return result;
        }
        String fileFormat = FileUtil.getFilenameExtension(file.getOriginalFilename());
        if (!Arrays.asList(UploadType.IMAGE.getFormats()).contains(fileFormat)) {
            result.put(Common.MSG, "文件格式错误");
            return result;
        }

        return uploadFileCore(file, "images");
    }

    /**
     * 上传文件
     *
     * @param file
     * @param uploadType
     * @return
     */
    public Map<String, Object> uploadFiles(MultipartFile file, UploadType uploadType) {
        String fileFormat = FileUtil.getFilenameExtension(file.getOriginalFilename());
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("error", 1);

        if (file.getSize() > uploadType.getMaxSize()) {
            result.put("message", "文件过大：不超过" + Utils.getSizeText(uploadType.getMaxSize()));
            return result;
        } else if (!Arrays.asList(uploadType.getFormats()).contains(fileFormat)) {
            result.put("message", "文件格式错误，支持的格式：" + Arrays.toString(uploadType.getFormats()));
        } else {
            result = uploadFileCore(file, uploadType.getFolder());
            if ((Boolean) result.get(Common.SUCCESS)) {
                String newFileName = (String) result.get(Common.SIMPLE_FILENAME);
                String newFilePath = (String) result.get(Common.FILEPATH);
                File localFile = new File(newFilePath);
                Result _result = uploadToOSS(localFile, uploadType.getOssKey() + newFileName);
                if (_result.isSuccess()) {
                    result.put(Common.SUCCESS, true);
                    result.put("error", 0);
                    result.put("url", _result.getMsg());
                } else {
                    result.put(Common.SUCCESS, false);
                    result.put(Common.MSG, _result.getMsg());
                }
            } else {
                result.put("message", result.get(Common.MSG));
            }
        }

        return result;
    }

    /**
     * 裁剪并缩放图片
     *
     * @param fileName
     * @param x
     * @param y
     * @param width
     * @param height
     * @return
     */
    public Result cropper(String fileName, double x, double y, double width, double height, String rootPath) {
        Result result = new Result();
        result.setSuccess(true);

        fileName = rootPath + fileName;
        try {
            Thumbnails.of(new File(fileName))
                    .sourceRegion((int) x, (int) y, (int) width, (int) height)
                    .size(Common.PHOTO_SIZE, Common.PHOTO_SIZE)
                    .toFile(new File(fileName));
            result.setInfo(fileName);
        } catch (IOException e) {
            logger.error(e);
            result.setSuccess(false);
            result.setMsg("内部错误");
        }

        return result;
    }

    /**
     * 上传文件核心方法
     *
     * @param file
     * @param childPath
     * @return
     */
    private Map<String, Object> uploadFileCore(MultipartFile file, String childPath) {
        Map<String, Object> result = new HashMap<String, Object>();
        String path = System.getProperty(Common.APP_NAME);
        File folder = new File(path + childPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        String newFileName = Utils.uuid() + FileUtil.getFileFormat(file.getOriginalFilename());
        String newFilePath = path + childPath + "/" + newFileName;
        try {
            file.transferTo(new File(newFilePath));
        } catch (IOException e) {
            result.put(Common.SUCCESS, false);
            result.put(Common.MSG, "上传失败，内部错误!");
            e.printStackTrace();
            return result;
        }
        result.put(Common.SUCCESS, true);
        result.put(Common.SIMPLE_FILENAME, newFileName);
        result.put(Common.FILENAME, childPath + "/" + newFileName);
        result.put(Common.FILEPATH, newFilePath);

        logger.info(newFilePath);
        return result;
    }

    /**
     * 上传到OSS
     *
     * @param file
     * @param key
     * @return
     */
    public Result uploadToOSS(File file, String key) {
        Result result = new Result();
        result.setSuccess(true);
        OSSClient ossClient = null;

        try {
            ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
            if (ossClient.doesBucketExist(bucketName)) {
                ossClient.putObject(new PutObjectRequest(bucketName, key, file));
                OSSObject object = ossClient.getObject(bucketName, key);
                if (object != null) {
                    result.setMsg(object.getResponse().getUri());
                } else {
                    result.setMsg("");
                }
            } else {
                result.setSuccess(false);
                result.setMsg("上传失败：文件系统不存在");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setMsg("上传失败：内部错误");
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }

        return result;
    }

}
