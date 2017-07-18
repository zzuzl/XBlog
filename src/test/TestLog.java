import cn.zzuzl.xblog.exception.ErrorCode;
import cn.zzuzl.xblog.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring-config.xml")
public class TestLog {
    private Logger logger = LogManager.getLogger(getClass());

    @Test
    public void testLog() {
        logger.info("test");
        logger.info("name:{},age:{}", "zhangsan", 22);
    }

    public void ex() {
        throw new ServiceException(ErrorCode.INTERNAL_SERVER_ERROR, "查询错误");
    }

    @Test
    public void testException() {
        try {
            ex();
        } catch (ServiceException e) {
            logger.error("service exception,id:{}", 1, e);
        } catch (Exception e) {
            logger.error("exception");
        }
    }

}
