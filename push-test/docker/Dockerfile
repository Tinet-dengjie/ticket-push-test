FROM openjdk:8u232-jdk

RUN ln -sf /usr/share/zoneinfo/Asia/ShangHai /etc/localtime \
    && echo "Asia/Shanghai" > /etc/timezone \
    && dpkg-reconfigure -f noninteractive tzdata

COPY entrypoint.sh /usr/local/bin/

RUN chmod +x /usr/local/bin/entrypoint.sh

COPY app.jar /usr/local/iot/app.jar

EXPOSE 8080

ENTRYPOINT ["/bin/sh", "-c", "/usr/local/bin/entrypoint.sh"]



