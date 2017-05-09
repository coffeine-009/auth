#- Base image -#
FROM java:8

#- Author -#
MAINTAINER Vitaliy Tsutsman <vitaliy.tsutsman@musician-virtuoso.com>

# Define dafault value of DB host
ENV DB_HOST localhost

#- Expose port -#
EXPOSE 8080

#- Run app -#
CMD [ "java", "-jar", "/opt/auth.jar" ]

#- Copy application -#
ADD ./build/libs/auth-*.jar /opt/auth.jar
