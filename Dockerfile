# The base image
FROM ubuntu:latest

MAINTAINER SIJUMON KARYIL RAJU <sijuthomas1988@gmail.com>
LABEL Description="Subscription API Image"

COPY target/subscription-api.jar /subscription-api.jar

CMD echo Docker container started.
CMD exec java -jar /subscription-api.jar
EXPOSE   8080
