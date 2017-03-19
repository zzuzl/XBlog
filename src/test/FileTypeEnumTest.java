import cn.zzuzl.xblog.model.UploadType;
import org.junit.Test;

/**
 * Created by Administrator on 2017/3/19.
 */
public class FileTypeEnumTest {

    @Test
    public void test() {
        UploadType type = UploadType.valueOfType("media");
        System.out.println(type);
    }
}
