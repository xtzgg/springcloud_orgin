package com.imooc.order.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

import java.util.concurrent.*;

@Slf4j
public class CyclicBarrierBadDemo {

    static final ExecutorService executorService =
            Executors.newFixedThreadPool(5,new CustomizableThreadFactory("lyy-"));

    public static void main(String[] args) throws Exception {

        CyclicBarrier awaitLock = new CyclicBarrier(11);

        for (int i = 0; i < 15; i++) {
            executorService.execute(() -> {
                try {
                    log.info("{}:等待",Thread.currentThread().getName());
                    awaitLock.await();
                    log.info("{}:唤醒",Thread.currentThread().getName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        log.info(Thread.currentThread().getName()+"等待");
         awaitLock.await();
        log.info(Thread.currentThread().getName()+"唤醒----");
    }
}