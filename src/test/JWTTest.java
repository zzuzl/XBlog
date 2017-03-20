import cn.zzuzl.xblog.model.User;
import cn.zzuzl.xblog.util.JsonUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.time.DateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.Date;

public class JWTTest {
    private Logger logger = LogManager.getLogger(getClass());

    @Test
    public void testCreateToken() {
        String token = null;
        User user = new User();
        user.setUserId(1);
        user.setEmail("672399171@qq.com");
        user.setNickname("张三");

        try {
            Algorithm algorithm = Algorithm.HMAC256("XBlog");
            Date date = new Date();
            token = JWT.create()
                    .withIssuer(JsonUtil.toJson(user))
                    .withIssuedAt(date)
                    .withExpiresAt(DateUtils.addMinutes(date, 30))
                    .sign(algorithm);
        } catch (UnsupportedEncodingException exception) {
            logger.error("UTF-8 encoding not supported");
        } catch (JWTCreationException exception) {
            logger.error("Invalid Signing configuration / Couldn't convert Claims.");
        }
        logger.info(token);
    }

    @Test
    public void testVerifyToken() {
        User user = new User();
        user.setUserId(1);
        user.setEmail("672399171@qq.com");
        user.setNickname("张三");
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE0ODk5OTAzOTksImlzcyI6IntcInVzZXJJZFwiOjEsXCJlbWFpbFwiOlwiNjcyMzk5MTcxQHFxLmNvbVwiLFwiaGFzaFwiOm51bGwsXCJzYWx0XCI6bnVsbCxcIm5pY2tuYW1lXCI6XCLlvKDkuIlcIixcInJlZ1RpbWVcIjpudWxsLFwiZmFuc0NvdW50XCI6MCxcImF0dGVudGlvbkNvdW50XCI6MCxcInBob3RvU3JjXCI6bnVsbCxcIm1vdHRvXCI6bnVsbCxcImludGVyZXN0XCI6bnVsbCxcInNleFwiOm51bGwsXCJ1cmxcIjpudWxsLFwicmFua1Njb3JlXCI6MH0iLCJpYXQiOjE0ODk5ODg1OTl9.KsCDhnQPxlYxfGOZMNydXI1W1jQ4a9GNpaf7OWJ2LHk";
        try {
            Algorithm algorithm = Algorithm.HMAC256("XBlog");
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            // Header Claims 头数据
            logger.info("alg:" + jwt.getAlgorithm()); // 算法
            logger.info("typ:" + jwt.getType());  // 类型
            logger.info("cty:" + jwt.getContentType()); // contentType
            logger.info("kid:" + jwt.getKeyId());  // keyId
            // Payload Claims 装载数据
            logger.info("iss:" + jwt.getIssuer()); // issuer 发行者
            logger.info("sub:" + jwt.getSubject()); // subject 主题
            logger.info("aud:" + jwt.getAudience()); // Audience 读者
            logger.info("exp:" + jwt.getExpiresAt()); // expiresAt 失效时间
            logger.info("nbf:" + jwt.getNotBefore()); // not before
            logger.info("iat:" + jwt.getIssuedAt()); // issued at
            logger.info("jti:" + jwt.getId()); // jwt id
            // Private Claims
            // ...
        } catch (UnsupportedEncodingException exception) {
            logger.error("UTF-8 encoding not supported");
        } catch (JWTVerificationException exception) {
            logger.error("Invalid signature/claims");
        }
    }
}
