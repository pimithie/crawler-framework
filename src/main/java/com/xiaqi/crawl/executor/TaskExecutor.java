package com.xiaqi.crawl.executor;

import com.xiaqi.crawl.fetcher.FetcherComposite;
import com.xiaqi.crawl.utils.URLUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.*;

@Slf4j
@Component
public class TaskExecutor {

    private ExecutorService threadpool;

    @Autowired
    private FetcherComposite fetcherComposite;

    /**
     * core size
     */
    private static final int DEFAULT_CORE_SIZE = Runtime.getRuntime().availableProcessors();

    /**
     * non-core size
     */
    private static final int DEFAULT_NON_CORE_SIZE = DEFAULT_CORE_SIZE;

    /**
     * non core thread timeout ,default 1 minute
     */
    private static final int DEFAULT_NON_CORE_THREAD_TIMEOUT_IN_SECONDS = 60;

    private static final int DEFAULT_TASK_QUEUE_SIZE = 10;

    /**
     * TODO: test the @Value annotation use the default value ${my.prop:myDefaultValue}
     */
    @Value("${threadpool.core:12}")
    private int coreSize;

    @Value("${threadpool.non.core:12}")
    private int nonCoreSize;

    @Value("${threadpool.non.core.timeout:60}")
    private int nonCoreThreadTimeoutInSeconds;

    @Value("${threadpool.task.queue:10}")
    private int taskQueueSize;

    /**
     * store the pending processed urls
     */
    private final Queue<String> URLQueue;

    private final Set<String> visitedURLs = Collections.synchronizedSet(new HashSet<>());

    public TaskExecutor() {
        this.URLQueue = new ConcurrentLinkedQueue<>();
    }

    @PostConstruct
    public void init() {
        this.threadpool = new ThreadPoolExecutor(coreSize,coreSize + nonCoreSize,nonCoreThreadTimeoutInSeconds, TimeUnit.SECONDS,new LinkedBlockingQueue<>(DEFAULT_TASK_QUEUE_SIZE));
    }

    public void setThreadpool(ExecutorService threadpool) {
        this.threadpool = threadpool;
    }

    public boolean executeTask(String url) {
        URLQueue.add(url);
        String domain = URLUtils.getDomain(url);
        while (! URLQueue.isEmpty()) {
            // note: may be have performance problem
            int size = URLQueue.size();
            int finalTaskSize = Math.min(size,coreSize + nonCoreSize + taskQueueSize);
            CountDownLatch countDownLatch = new CountDownLatch(finalTaskSize);
            for (int i = 0;i < finalTaskSize;i++) {
                Runnable r = new CrawlerTask(countDownLatch,domain,URLQueue.poll());
                this.threadpool.execute(r);
            }
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                log.error("thread is interrupted!",e);
                return false;
            }
        }
        return true;
    }

    public Queue<String> getURLQueue() {
        return URLQueue;
    }

    public Set<String> getVisitedURLs() {
        return visitedURLs;
    }

    private class CrawlerTask implements Runnable {

        private final CountDownLatch countDownLatch;

        private final String domain;

        private String url;

        public CrawlerTask(CountDownLatch countDownLatch, String domain,String url) {
            this.countDownLatch = countDownLatch;
            this.domain = domain;
        }

        @Override
        public void run() {
            try {
                if (TaskExecutor.this.visitedURLs.contains(url) || ! URLUtils.getDomain(url).contains(domain)) {
                    return;
                }
                TaskExecutor.this.visitedURLs.add(url);
                TaskExecutor.this.fetcherComposite.fetch(url);
            } finally {
                countDownLatch.countDown();
            }
        }
    }
}
