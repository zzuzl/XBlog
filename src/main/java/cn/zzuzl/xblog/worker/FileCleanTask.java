package cn.zzuzl.xblog.worker;

import cn.zzuzl.xblog.common.Common;
import cn.zzuzl.xblog.util.ConfigProperty;
import cn.zzuzl.xblog.util.FileUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 定时清理文件
 */
@Component
public class FileCleanTask {
    private Logger logger = LogManager.getLogger(getClass());
    private static final long timeGap = 10 * 60 * 1000;
    private static final long fileTimeGap = 60 * 60 * 1000;
    @Resource
    private ConfigProperty configProperty;

    public void execute() {
        logger.info("文件清理worker执行start...");
        try {
            String rootPath = System.getProperty(Common.APP_NAME);
            int count = 0;
            // 清理上传头像的文件目录
            count += FileUtil.cleanFiles(rootPath + configProperty.getPhotoFolder(), timeGap);
            // 清理上传的媒体文件
            count += FileUtil.cleanFiles(rootPath + configProperty.getUploadMediaFolder(), fileTimeGap);
            // 清理上传的普通文件
            count += FileUtil.cleanFiles(rootPath + configProperty.getUploadFileFolder(), fileTimeGap);
            // 清理上传的图片文件
            count += FileUtil.cleanFiles(rootPath + configProperty.getUploadImageFolder(), fileTimeGap);
            // 清理上传的flash文件
            count += FileUtil.cleanFiles(rootPath + configProperty.getUploadFlashFolder(), fileTimeGap);
            logger.info("清理文件数:" + count);
        } catch (Exception e) {
            logger.error("文件清理发生错误:" + e.getMessage());
            e.printStackTrace();
        }

        logger.info("文件清理worker执行end...");
    }
}
