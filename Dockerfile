
FROM  adoptopenjdk/openjdk8:alpine-slim
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
COPY ./target/KotlinSecurity-0.0.1-SNAPSHOT.jar /usr/app/
WORKDIR /usr/app

ENTRYPOINT ["java","-jar","KotlinSecurity-0.0.1-SNAPSHOT.jar"]

EXPOSE 10001