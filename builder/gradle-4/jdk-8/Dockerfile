# #####################################################################
# Build and pull dependencies
# #####################################################################
# Build Arguments to be received from a build pipeline
# Use --build-arg <varname>=<value> during docker build
# https://github.com/moby/moby/issues/15025#issuecomment-371466934
# #####################################################################

FROM gradle:4.10.1-jdk8
MAINTAINER marcello.desales@gmail.com
USER root

ONBUILD ARG BUILDER_GIT_SHA
ONBUILD ARG BUILDER_GIT_BRANCH
ONBUILD ARG BUILDER_GRADLE_DOWNLOAD_DEPENDENCIES_CMD
ONBUILD ARG BUILDER_GRADLE_BUILD_CMD

ONBUILD ENV BUILD_TAG=sha:${BUILDER_GIT_SHA},build:${BUILDER_GIT_BRANCH}
ONBUILD ENV GIT_SHA=${BUILDER_GIT_SHA} GIT_BRANCH=${BUILDER_GIT_BRANCH}

WORKDIR /app/

ONBUILD ADD ./build.gradle build.gradle
ONBUILD ADD ./settings.gradle settings.gradle
ONBUILD ADD ./src/gradle src/gradle

# https://stackoverflow.com/questions/21814652/how-to-download-dependencies-in-gradle/44168582#44168582
# Almost **all** dependencies, but helps separating a few before the source-code
ONBUILD RUN echo "Executing BUILDER_GRADLE_DOWNLOAD_DEPENDENCIES_CMD='${BUILDER_GRADLE_DOWNLOAD_DEPENDENCIES_CMD}'"
ONBUILD RUN ${BUILDER_GRADLE_DOWNLOAD_DEPENDENCIES_CMD}

ONBUILD ADD ./src src/
ONBUILD RUN echo "Executing BUILDER_GRADLE_BUILD_CMD='${BUILDER_GRADLE_BUILD_CMD}'"
ONBUILD RUN ${BUILDER_GRADLE_BUILD_CMD}
