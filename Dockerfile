FROM openjdk:8-jdk-alpine as supermarket-builder
ARG WORKDIR=/usr/local/src/supermarket
RUN mkdir -p ${WORKDIR}
COPY . ${WORKDIR}
WORKDIR ${WORKDIR}
RUN ./gradlew build -x test

FROM openjdk:8-jdk-alpine
RUN apk --update add iputils curl #postgresql-client redis
RUN addgroup -S supermarket && adduser -S supermarket -G supermarket
USER supermarket:supermarket
ARG WORKDIR=/home/supermarket
WORKDIR ${WORKDIR}
COPY --from=supermarket-builder /usr/local/src/supermarket/build/libs/*.jar ${WORKDIR}/app.jar
ENTRYPOINT exec java $JAVA_OPTS -jar app.jar
# ENTRYPOINT tail -f /dev/null