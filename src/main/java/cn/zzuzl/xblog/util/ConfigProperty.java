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

    @Value("${oss.photo-pic}")
    private String ossPhotoPic;

    @Value("${clean.photo.folder}")
    private String photoFolder;

    @Value("${clean.uploadMedia.folder}")
    private String uploadMediaFolder;

    @Value("${clean.uploadFile.folder}")
    private String uploadFileFolder;

    @Value("${clean.uploadImage.folder}")
    private String uploadImageFolder;

    @Value("${clean.uploadFlash.folder}")
    private String uploadFlashFolder;

    @Value("${token.secret}")
    private String secret;

    public String getRoot() {
        return "http://" + domain;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public String getUsername() {
        return username;
    }

    public String getOssPhotoPic() {
        return ossPhotoPic;
    }

    public String getPhotoFolder() {
        return photoFolder;
    }

    public String getUploadMediaFolder() {
        return uploadMediaFolder;
    }

    public String getUploadFileFolder() {
        return uploadFileFolder;
    }

    public String getUploadImageFolder() {
        return uploadImageFolder;
    }

    public String getUploadFlashFolder() {
        return uploadFlashFolder;
    }

    public String getSecret() {
        return secret;
    }
}
