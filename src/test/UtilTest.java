import cn.zzuzl.xblog.util.FileUtil;
import org.junit.Test;

/**
 * Created by Administrator on 2017/4/9.
 */
public class UtilTest {
    @Test
    public void testUtil() {
        // System.out.println(FileUtil.getFileName("D:/dawd/1.txt"));
        System.out.println(FileUtil.getFilenameExtension("1.txt"));
    }
}
