package cn.zzuzl.xblog.service;

import cn.zzuzl.xblog.dao.UserDao;
import cn.zzuzl.xblog.model.vo.TokenVerifyResult;
import cn.zzuzl.xblog.util.ConfigProperty;
import cn.zzuzl.xblog.util.JsonUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.commons.lang.time.DateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.Date;

@Service
public class TokenService {
    @Resource
    private UserDao userDao;
    @Resource
    private ConfigProperty configProperty;
    private Logger logger = LogManager.getLogger(getClass());

    public String generateToken(Integer userId) {
        String token = null;
        try {
            Algorithm algorithm = Algorithm.HMAC256(configProperty.getSecret());
            Date date = new Date();
            token = JWT.create()
                    .withIssuer(String.valueOf(userId))
                    .withExpiresAt(DateUtils.addMinutes(date, 30))
                    .sign(algorithm);
        } catch (UnsupportedEncodingException exception) {
            logger.error("UTF-8 encoding not supported");
        } catch (JWTCreationException exception) {
            logger.error("Invalid Signing configuration / Couldn't convert Claims.");
        }
        return token;
    }

    public TokenVerifyResult verifyToken(String token) {
        TokenVerifyResult result = new TokenVerifyResult();
        try {
            Algorithm algorithm = Algorithm.HMAC256(configProperty.getSecret());
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            if (new Date().before(jwt.getExpiresAt())) {
                result.setValid(true);
                result.setExp(jwt.getExpiresAt());
                result.setUserId(Integer.parseInt(jwt.getIssuer()));
            }
        } catch (UnsupportedEncodingException exception) {
            logger.error("UTF-8 encoding not supported");
            result.setValid(false);
        } catch (JWTVerificationException exception) {
            logger.error("Invalid signature/claims");
            result.setValid(false);
        } catch (Exception e) {
            e.printStackTrace();
            result.setValid(false);
        }
        return result;
    }

}
