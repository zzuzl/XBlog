import cn.zzuzl.xblog.common.Common;
import cn.zzuzl.xblog.model.Article;
import cn.zzuzl.xblog.service.RedisService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2017/3/19.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring-config.xml")
public class RedisTest {
    @Resource
    private RedisService redisService;
    @Resource
    private RedisTemplate<Object,Object> redisTemplate;

    @Test
    public void getKey() {
        Article article = redisService.queryArticleFromCacheById(58);
        System.out.println(article);
    }

    @Test
    public void deleteKey() {
        Long result = redisTemplate.opsForHash().delete(String.valueOf(Common.KEY_ARTICLEDETAIL), String.valueOf(58));
        System.out.println(result);
        System.out.println(redisTemplate.opsForHash().hasKey(Common.KEY_ARTICLEDETAIL, String.valueOf(58)));
    }
}
