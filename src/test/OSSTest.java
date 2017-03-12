import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import org.junit.Test;

import java.io.File;

public class OSSTest {
    private static String endpoint = "http://oss-cn-shanghai.aliyuncs.com";
    private static String accessKeyId = "LTAIt4St90z5n4ZK";
    private static String accessKeySecret = "hEm0M3TDbqmZv77Vvhxwx2qU5GReM6";
    private static String bucketName = "xblog-mis";
    private static String key = "1.png";

    @Test
    public void testUpload() {
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        System.out.println("Getting Started with OSS SDK for Java");

        if (ossClient.doesBucketExist(bucketName)) {
            System.out.println("Uploading a new object to OSS from a file\n");
            PutObjectResult result = ossClient.putObject(new PutObjectRequest(bucketName, key, new File("E:/default-head-photo.png")));
            System.out.println(result);
            ossClient.shutdown();
        } else {
            // 不存在
        }
    }

    @Test
    public void testGetObject() {
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        OSSObject object = ossClient.getObject(bucketName,key);
        System.out.println(object.getResponse().getUri());
    }

}
