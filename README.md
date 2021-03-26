# HealthCheck Service

This is a simple health check service that takes two arguments
- service url
- time in seconds

It continuously monitors the health of the service and write the logs in the file
In case of any interruption in the running service, it shutdowns.

# Technology

- Java
- Maven
- Log4j

# Usage

`java -jar healthchecker.jar`
`java -jar healthchecker.jar http://localhost:8080 5`

# Test

- The test runs the executor service for 10 seconds with 5 seconds interval and then shutdowns

# Improvements

- Include some more test

# Question

- Not sure if the cloud administrator wanted to restart the service automatically in case of any interruption.
