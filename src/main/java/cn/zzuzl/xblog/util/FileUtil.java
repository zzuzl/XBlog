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

    /**
     * 清理目录文件
     * @param folder
     * @param timeGap
     * @return
     */
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

    /**
     * 根据路径获取文件名
     * @param path
     * @return
     */
    public static String getFileName(String path) {
        return StringUtils.getFilename(path);
    }

    /**
     * 根据路径获取文件格式
     * @param path
     * @return
     */
    public static String getFilenameExtension(String path) {
        return StringUtils.getFilenameExtension(path);
    }

    /**
     * 获取文件格式
     * @param path
     * @return
     */
    public static String getFileFormat(String path) {
        String extension = getFilenameExtension(path);
        if(!StringUtils.isEmpty(extension)) {
            extension = "." + extension;
        }
        return extension;
    }
}
