FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY target/bfh-java-qualifier-1.0.0.jar app.jar
ENV JAVA_OPTS=""
ENV APP_NAME="John Doe"
ENV APP_REGNO="REG12346"
ENV APP_EMAIL="john@example.com"
ENTRYPOINT [ "sh", "-lc", "java $JAVA_OPTS -jar app.jar" ]
