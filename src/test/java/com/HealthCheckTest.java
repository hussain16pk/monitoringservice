package com;

import com.test.HealthCheck;
import org.junit.jupiter.api.Test;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class HealthCheckTest {

    @Test
    public void testWith5Seconds() throws InterruptedException {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        HealthCheck healthCheck = new HealthCheck();
        healthCheck.getHealthCheckRunnable();
        executor.scheduleAtFixedRate(healthCheck.getHealthCheckRunnable(), 0, 5, TimeUnit.SECONDS);
        Thread.sleep(10000);
        executor.shutdown();
    }
}
