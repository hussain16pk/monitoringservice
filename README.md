#HealthCheck Service

This is a simple health check service that takes two arguments
- service url
- time in seconds

It continuously monitors the health of the service and write the logs in the file
In case of any interruption in the running service, it shutdowns.

#Technology

- Java
- Maven
- Log4j

#Usage

`java -jar healthchecker-1.0-SNAPSHOT.jar`

#Test

- The test runs the executor service for 10 seconds with 5 seconds interval and then shutdowns

