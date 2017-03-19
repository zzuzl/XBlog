package cn.zzuzl.xblog.model;

/**
 * Created by Administrator on 2016/6/9.
 */

/**
 * 上传类型枚举
 */
public enum UploadType {
    MEDIA("media", "uploadMedia",
            new String[]{"swf", "flv", "mp3", "wav", "wma", "wmv", "mid", "avi", "mpg", "asf", "rm", "rmvb"},
            100 * 1024 * 1024, "xblog/files/uploadMedia/"),
    FILE("file", "uploadFile",
            new String[]{"doc", "docx", "xls", "xlsx", "ppt", "htm", "html", "txt", "zip", "rar", "gz", "bz2"},
            50 * 1024 * 1024, "xblog/files/uploadFile/"),
    IMAGE("image", "uploadImage",
            new String[]{"gif", "jpg", "jpeg", "png", "bmp", "GIF", "JPG", "JPEG", "PNG", "BMP"},
            10 * 1024 * 1024, "xblog/files/uploadImage/"),
    FLASH("flash", "uploadFlash",
            new String[]{"swf", "flv"},
            30 * 1024 * 1024, "xblog/files/uploadFlash/");

    private final String type;
    private final String folder;
    private final String[] formats;
    private final long maxSize;
    private final String ossKey;

    private UploadType(String type, String folder, String[] formats, long maxSize, String ossKey) {
        this.type = type;
        this.folder = folder;
        this.formats = formats;
        this.maxSize = maxSize;
        this.ossKey = ossKey;
    }

    public static UploadType valueOfType(String type) {
        for (UploadType uploadType : UploadType.values()) {
            if (uploadType.getType().equals(type)) {
                return uploadType;
            }
        }

        return null;
    }

    public String getType() {
        return type;
    }

    public String getFolder() {
        return folder;
    }

    public String[] getFormats() {
        return formats;
    }

    public long getMaxSize() {
        return maxSize;
    }

    public String getOssKey() {
        return ossKey;
    }
}
