package cn.zzuzl.xblog.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * json util
 */
public class JsonUtil {
    private static Logger logger = LogManager.getLogger(JsonUtil.class);
    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * 转化到json
     *
     * @param object
     * @return
     */
    public static String toJson(Object object) {
        String result = null;
        try {
            result = mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            logger.error("json error:" + e.getMessage());
        }
        return result;
    }

    /**
     * 从json转化
     *
     * @param jsonStr
     * @param valueType
     * @param <T>
     * @return
     */
    public static <T> T fromJson(String jsonStr, Class<T> valueType) {
        T result = null;
        try {
            result = mapper.readValue(jsonStr, valueType);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("json error:" + e.getMessage());
        }
        return result;
    }
}
