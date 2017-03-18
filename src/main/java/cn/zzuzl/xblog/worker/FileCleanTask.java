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
    @Resource
    private ConfigProperty configProperty;

    public void execute() {
        logger.info("文件清理worker执行start...");
        try {
            String rootPath = System.getProperty(Common.APP_NAME);
            String photoFolderPath = rootPath + configProperty.getPhotoFolder();
            int count = 0;
            count += FileUtil.cleanFiles(photoFolderPath, timeGap);
            logger.info("清理文件数:" + count);
        } catch (Exception e) {
            logger.error("文件清理发生错误:" + e.getMessage());
            e.printStackTrace();
        }

        logger.info("文件清理worker执行end...");
    }
}
