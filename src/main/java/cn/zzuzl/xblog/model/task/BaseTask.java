package cn.zzuzl.xblog.model.task;

public interface BaseTask {
    void lock();

    void success();

    void fail();
}
