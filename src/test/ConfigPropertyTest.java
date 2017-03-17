import cn.zzuzl.xblog.service.MailService;
import cn.zzuzl.xblog.util.ConfigProperty;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring-config.xml")
public class ConfigPropertyTest {
    @Resource
    private ConfigProperty configProperty;
    @Resource
    private MailService mailService;

    @Test
    public void testProperty() {
        System.out.println(configProperty.getFromAddress());
        System.out.println(configProperty.getRoot());
    }

    @Test
    public void testMail() {
        mailService.sendResetPwdEmail("672399171@qq.com","dawdawdawd");
    }
}
