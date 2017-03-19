package cn.zzuzl.xblog.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 配置文件properties
 */
@Component
public class ConfigProperty {
    @Value("${domain}")
    private String domain;

    @Value("${mail.from}")
    private String fromAddress;

    @Value("${mail.username}")
    private String username;

    @Value("${clean.photo.folder}")
    private String photoFolder;

    @Value("${oss.photo-pic}")
    private String ossPhotoPic;

    @Value("${oss.files.uploadMedia}")
    private String ossFilesMedia;

    @Value("${oss.files.uploadFile}")
    private String ossFilesFile;

    @Value("${oss.files.uploadImage}")
    private String ossFilesImage;

    @Value("${oss.files.uploadFlash}")
    private String ossFilesFlash;

    public String getRoot() {
        return "http://" + domain;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public String getUsername() {
        return username;
    }

    public String getPhotoFolder() {
        return photoFolder;
    }

    public String getOssPhotoPic() {
        return ossPhotoPic;
    }

    public String getOssFilesMedia() {
        return ossFilesMedia;
    }

    public String getOssFilesFile() {
        return ossFilesFile;
    }

    public String getOssFilesImage() {
        return ossFilesImage;
    }

    public String getOssFilesFlash() {
        return ossFilesFlash;
    }
}
