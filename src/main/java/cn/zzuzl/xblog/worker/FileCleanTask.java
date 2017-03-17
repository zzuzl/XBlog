package cn.zzuzl.xblog.worker;

import cn.zzuzl.xblog.common.Common;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * 定时清理文件
 */
@Component
public class FileCleanTask {
    private Logger logger = LogManager.getLogger(getClass());

    public void execute() {
        logger.info("文件清理worker执行start...");
        try {
            // D:\idea\projects\XBlog\target\XBlog\
            String rootPath = System.getProperty(Common.APP_NAME);
        } catch (Exception e) {
            logger.error("文件清理发生错误:" + e.getMessage());
            e.printStackTrace();
        }

        logger.info("文件清理worker执行end...");
    }
}
