FROM openjdk:11-jre-slim

ENV JAVA_OPTS="-XX:+UseG1GC -Xmx512m -XX:+UnlockExperimentalVMOptions -XX:+UseContainerSupport -Duser.timezone=Asia/Taipei -Djava.security.egd=file:/dev/./urandom"

RUN echo "Asia/Taipei" > /etc/timezone
WORKDIR /app

COPY docker/entrypoint.sh /app/entrypoint.sh
RUN chmod +x /app/entrypoint.sh
COPY json/ /app/json/

EXPOSE 8080

COPY build/libs/crypto.jar /app/crypto.jar

ENTRYPOINT ["./entrypoint.sh"]