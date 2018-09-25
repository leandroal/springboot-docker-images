# #####################################################################
# Build and pull dependencies
# #####################################################################
# Build Arguments to be received from a build pipeline
# Use --build-arg <varname>=<value> during docker build
# https://github.com/moby/moby/issues/15025#issuecomment-371466934
# #####################################################################

FROM maven:3.5.4-jdk-8
MAINTAINER marcello.desales@gmail.com
USER root

ONBUILD ARG BUILDER_GIT_SHA
ONBUILD ARG BUILDER_GIT_BRANCH
ONBUILD ARG BUILDER_MAVEN_DOWNLOAD_DEPENDENCIES_CMD
ONBUILD ARG BUILDER_MAVEN_BUILD_CMD

ONBUILD ENV BUILD_TAG=sha:${BUILDER_GIT_SHA},build:${BUILDER_GIT_BRANCH}
ONBUILD ENV GIT_SHA=${BUILDER_GIT_SHA} GIT_BRANCH=${BUILDER_GIT_BRANCH}

WORKDIR /app/

ONBUILD ADD ./pom.xml pom.xml
ONBUILD ADD ./settings.xml settings.xml

# Download the dependencies only if pom.xml or settings.xml have changed
ONBUILD RUN echo "Executing BUILDER_MAVEN_DOWNLOAD_DEPENDENCIES_CMD='${BUILDER_MAVEN_DOWNLOAD_DEPENDENCIES_CMD}'"
ONBUILD RUN ${BUILDER_MAVEN_DOWNLOAD_DEPENDENCIES_CMD}

# Build the source-code after downloading the dependencies
ONBUILD ADD ./src src/
ONBUILD RUN echo "Executing BUILDER_MAVEN_BUILD_CMD=${BUILDER_MAVEN_BUILD_CMD}"
ONBUILD RUN ${BUILDER_MAVEN_BUILD_CMD}
