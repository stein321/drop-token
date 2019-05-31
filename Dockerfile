FROM openjdk:11
COPY target/drop-token.jar /usr/src/drop/
WORKDIR /usr/src/drop/
CMD ["./drop-token.jar"]