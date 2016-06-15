package com.zzu.xblog.model;

/**
 * Created by Administrator on 2016/6/9.
 */

/**
 * 上传类型枚举
 */
public enum UploadType {
    MEDIA("media", "uploadMedia",
            new String[]{"swf", "flv", "mp3", "wav", "wma", "wmv", "mid", "avi", "mpg", "asf", "rm", "rmvb"}, 10 * 1024 * 1024),
    FILE("file", "uploadFile",
            new String[]{"doc", "docx", "xls", "xlsx", "ppt", "htm", "html", "txt", "zip", "rar", "gz", "bz2"}, 10 * 1024 * 1024),
    IMAGE("image", "uploadImage",
            new String[]{"gif", "jpg", "jpeg", "png", "bmp","GIF", "JPG", "JPEG", "PNG", "BMP"}, 1024 * 1024),
    FLASH("flash", "uploadFlash",
            new String[]{"swf", "flv"}, 10 * 1024 * 1024);

    private final String type;
    private final String folder;
    private final String[] formats;
    private final long maxSize;

    private UploadType(String type, String folder, String[] formats, long maxSize) {
        this.type = type;
        this.folder = folder;
        this.formats = formats;
        this.maxSize = maxSize;
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
}
