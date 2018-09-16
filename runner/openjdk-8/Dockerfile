# #####################################################################
# Build stage for running the Executable SpringBoot WAR files
#
# 1. Use a FROM gradle:4.10.1-jdk8 as spring-boot-package-builder
# 2. Build your steps needed
# 3. For runtime image use FROM marcellodesales/spring-boot-package-runner
#    - It will copy the generate WAR and resources properly
# * Look at background info https://spring.io/guides/gs/spring-boot-docker/
# * https://github.com/moby/moby/issues/15025#issuecomment-371466934
# #####################################################################
FROM openjdk:8-jre-slim
MAINTAINER marcello.desales@gmail.com

# This is to support HTTPS calls to other services
RUN apt-get update && apt-get install -y curl ca-certificates
RUN update-ca-certificates && mkdir -p /usr/share/ssl/certs && \
    chmod 755 /usr/share/ssl/certs

WORKDIR /runtime

ONBUILD ARG EXECUTABLE_TASK_TYPE
ONBUILD ENV EXECUTABLE_TASK_TYPE=${EXECUTABLE_TASK_TYPE}

# Copy from the previous stage
ONBUILD RUN echo "Will copy from spring-boot-package-builder /app/build/libs/*.${EXECUTABLE_TASK_TYPE} /tmp"
ONBUILD COPY --from=marcellodesales-springboot-builder /app/build/libs/*.${EXECUTABLE_TASK_TYPE} /tmp
ONBUILD RUN ls -la /tmp/*.${EXECUTABLE_TASK_TYPE}

ONBUILD RUN echo "Will copy from /app/src/main/resources /runtime/resources"
ONBUILD COPY --from=marcellodesales-springboot-builder /app/src/main/resources /runtime/resources
ONBUILD RUN ls -la /runtime/resources

# Just rename the built version
ONBUILD RUN echo "Renaming the executable ${EXECUTABLE_TASK_TYPE} to the runtime dir"
ONBUILD RUN find /tmp -name "*.${EXECUTABLE_TASK_TYPE}" ! -name "*sources*" ! -name "*javadoc*" -exec cp -t /runtime {} + && \
   mv /runtime/*.${EXECUTABLE_TASK_TYPE} /runtime/server.jar && \
   rm -f /tmp/*.${EXECUTABLE_TASK_TYPE}

# What to execute on docker run
ENTRYPOINT sh -c "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom \
           $JAVA_PARAMS -jar /runtime/server.jar $SPRING_BOOT_APP_OPTS"
