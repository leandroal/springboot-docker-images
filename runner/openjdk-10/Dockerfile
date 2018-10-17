# #####################################################################
# Build stage for running the Executable SpringBoot WAR files
#
# 1. Use a FROM gradle:4.10.2-jdk10 as spring-boot-package-builder
# 2. Build your steps needed
# 3. For runtime image use FROM marcellodesales/spring-boot-package-runner
#    - It will copy the generate WAR and resources properly
# * Look at background info https://spring.io/guides/gs/spring-boot-docker/
# * https://github.com/moby/moby/issues/15025#issuecomment-371466934
# #####################################################################
FROM openjdk:10-jre-slim
MAINTAINER marcello.desales@gmail.com
MAINTAINER leandromsales@gmail.com

# ca certificates: to support HTTPS calls to other services
RUN apt-get update && apt-get install -y curl ca-certificates
RUN update-ca-certificates && mkdir -p /usr/share/ssl/certs && \
    chmod 755 /usr/share/ssl/certs

# libapr: to support Tomcat production http://tomcat.apache.org/native-doc/
# https://stackoverflow.com/questions/4278047/apr-based-apache-tomcat-native-library-was-not-found-on-the-java-library-path
# RUN apt-get install -y add-apt-repository
# RUN add-apt-repository ppa:ondrej/apache2 && apt-get update && apt-get install -y libapr1.0-dev libssl-dev

WORKDIR /runtime

ONBUILD ARG RUNNER_EXTENSION
ONBUILD ENV RUNNER_EXTENSION=${RUNNER_EXTENSION}

ONBUILD ARG RUNNER_DIR
ONBUILD ENV RUNNER_DIR=${RUNNER_DIR}

ONBUILD ARG RUNNER_PORT
ONBUILD ENV RUNNER_PORT=${RUNNER_PORT}

# Copy from the previous stage
ONBUILD RUN echo "Will copy from marcellodesales-springboot-builder /app/${RUNNER_DIR}/*.${RUNNER_EXTENSION} /tmp"
ONBUILD COPY --from=marcellodesales-springboot-builder /app/${RUNNER_DIR}/ /tmp
ONBUILD RUN ls -la /tmp/*.${RUNNER_EXTENSION}

ONBUILD RUN echo "Will copy from /app/src/main/resources /runtime/resources"
ONBUILD COPY --from=marcellodesales-springboot-builder /app/src/main/resources /runtime/resources
ONBUILD RUN ls -la /runtime/resources

# Just rename the built version
ONBUILD RUN echo "Renaming the executable ${RUNNER_EXTENSION} to the runtime dir"
ONBUILD RUN find /tmp -name "*.${RUNNER_EXTENSION}" ! -name "*sources*" ! -name "*javadoc*" -exec cp -t /runtime {} + && \
   mv /runtime/*.${RUNNER_EXTENSION} /runtime/server.jar && \
   rm -rf /tmp/*

ONBUILD EXPOSE ${RUNNER_PORT}

# What to execute on docker run
ENTRYPOINT sh -c "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom \
           $JAVA_PARAMS -jar /runtime/server.jar $SPRING_BOOT_APP_OPTS"
