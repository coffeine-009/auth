#- Base image -#
FROM java:8

#- Author -#
MAINTAINER Vitaliy Tsutsman <vitaliy.tsutsman@musician-virtuoso.com>

#- Create app dir -#
RUN mkdir /opt/auth

# Define dafault value of DB host
ENV DB_HOST localhost

#- Set up work dir -#
WORKDIR /opt/auth

ADD ./build/libs/auth-1.0.1-SNAPSHOT.jar /usr/src/auth.jar

#- Expose port -#
EXPOSE 8080

#- Run app -#
CMD [ "java", "-jar", "/usr/src/auth.jar" ]
