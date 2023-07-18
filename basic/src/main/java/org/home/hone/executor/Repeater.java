package org.home.hone.executor;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Repeater {
    public static void main(String[] args) {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(10);
        executor.scheduleAtFixedRate(
            () -> System.out.printf("Invoked at %d \n", System.currentTimeMillis()),
            2000,
            4000,
            TimeUnit.MILLISECONDS
        );
    }
}
