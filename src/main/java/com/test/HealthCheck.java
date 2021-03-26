package com.test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HealthCheck {

    private static final Logger logger = LogManager.getLogger(HealthCheck.class);
    private static String serviceUrl = "http://localhost:12345";
    private static int intervalInSeconds = 15;
    private final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();
    private ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

    public static void main(String[] args) {
        if (args.length == 2) {
            if (!notValidArguments(args)) {
                logger.error("Please enter correct service url and seconds. " +
                        "For example http://website.com and seconds should be greater than 0");
                return;
            }
        } else {
            logger.warn("Running in default mode with service url http://localhost:12345 and seconds: 15");
        }
        HealthCheck healthCheck = new HealthCheck();
        healthCheck.startService();
    }

    private static boolean notValidArguments(String[] args) {
        try {
            serviceUrl = args[0];
            intervalInSeconds = Integer.valueOf(args[1]);
            if (intervalInSeconds < 0) {
                return false;
            }
        } catch (NumberFormatException e) {
            logger.error("Invalid input");
            return false;
        }
        return true;
    }

    public void startService() {
        executor.scheduleAtFixedRate(getHealthCheckRunnable(), 0, intervalInSeconds, TimeUnit.SECONDS);
    }

    public Runnable getHealthCheckRunnable() {
        Runnable healthCheckRunnable = () -> {
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create(serviceUrl))
                    .setHeader("User-Agent", "HealthCheck Bot")
                    .build();
            HttpResponse<String> response = null;
            try {
                response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                logger.info("Service is up and running, status code: " + response.statusCode());
            } catch (IOException e) {
                logger.error("Service is not available. service url {} ", serviceUrl);
                logger.error(e);
            } catch (InterruptedException e) {
                logger.error("The health checker needs a restart as there is a interruption. service url {} ", serviceUrl);
                logger.error(e);
                logger.error("Health checker is shutting down.");
                executor.shutdown();
                logger.error("Shutdown.");
            }
        };
        return healthCheckRunnable;
    }
}
