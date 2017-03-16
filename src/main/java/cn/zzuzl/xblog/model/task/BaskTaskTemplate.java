package cn.zzuzl.xblog.model.task;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public abstract class BaskTaskTemplate<T extends BaseTask> {
    private ThreadPoolExecutor threadPool;
    private int corePoolSize = 2;
    private int maximumPoolSize = 10;
    private long keepAliveTime = 3;
    private int queueCapacity = 5000;
    private Logger logger = LogManager.getLogger(getClass());

    public abstract List<T> getTasks();

    public abstract void process(T task) throws Exception;

    /**
     * 批量执行
     */
    public void batchExecute() {
        final List<T> tasks = getTasks();

        if (tasks == null || tasks.isEmpty()) {
            return;
        }

        if (threadPool == null) {
            threadPool = new ThreadPoolExecutor(corePoolSize,
                    maximumPoolSize,
                    keepAliveTime,
                    TimeUnit.SECONDS,
                    new LinkedBlockingQueue<Runnable>(queueCapacity));
        }

        List<Future<?>> futures = new ArrayList<Future<?>>();
        for(final T task : tasks) {
            Future<?> future = threadPool.submit(new Runnable() {
                public void run() {
                    try {
                        task.lock();
                        process(task);
                        task.success();
                    } catch (Exception e) {
                        task.fail();
                    }
                }
            });
            futures.add(future);
        }

        for(Future<?> future : futures) {
            try {
                future.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }
}
