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

    public String getRoot() {
        return "http://" + domain;
    }

    public String getFromAddress() {
        return fromAddress;
    }
}