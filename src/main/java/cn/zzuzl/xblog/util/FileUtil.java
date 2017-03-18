package cn.zzuzl.xblog.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.StringUtils;

import java.io.File;

/**
 * Created by Q&M on 2017/3/18.
 */
public class FileUtil {
    private static Logger logger = LogManager.getLogger(FileUtil.class);

    // 清理目录文件
    public static int cleanFiles(String folder, long timeGap) {
        int count = 0;
        if (StringUtils.isEmpty(folder)) {
            return count;
        }
        File rootFolder = new File(folder);

        if (rootFolder.exists()) {
            if (!rootFolder.isDirectory()) {
                // 删除10分钟之前的文件
                if (System.currentTimeMillis() - rootFolder.lastModified() > timeGap) {
                    if (rootFolder.delete()) {
                        logger.info("清理:" + rootFolder.getAbsolutePath());
                        count++;
                    } else {
                        logger.error("文件删除失败");
                    }
                }
            } else {
                File[] files = rootFolder.listFiles();
                if (files != null && files.length > 0) {
                    for (File file : files) {
                        count += cleanFiles(file.getAbsolutePath(), timeGap);
                    }
                }
            }
        }
        return count;
    }
}
