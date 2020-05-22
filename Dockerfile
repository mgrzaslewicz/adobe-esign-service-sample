# Run it after mvn package when jar file is already built
FROM adoptopenjdk/openjdk13:alpine
ADD target/adobe-esign-service-sample*.jar /app/adobe-esign-service-sample.jar

WORKDIR /app
RUN mkdir -p /app/data
EXPOSE 80
ENTRYPOINT ["java", "-XX:+ExitOnOutOfMemoryError", "-XX:+HeapDumpOnOutOfMemoryError", "-XX:HeapDumpPath=/app/data", "-XX:+PrintFlagsFinal", "-XX:MaxRAMPercentage=75.0", "-Djava.security.egd=file:/dev/./urandom", "-jar", "adobe-esign-service-sample.jar"]
