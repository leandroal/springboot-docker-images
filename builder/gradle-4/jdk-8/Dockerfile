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

ONBUILD ARG GIT_SHA
ONBUILD ARG GIT_BRANCH
ONBUILD ARG BUILD_NUMBER
ONBUILD ARG GRADLE_BUILD_TASKS

ONBUILD ENV BUILD_TAG=sha:${GIT_SHA},build:${GIT_BRANCH}/${BUILD_NUMBER}
ONBUILD ENV GIT_SHA=${GIT_SHA} GIT_BRANCH=${GIT_BRANCH}

WORKDIR /app/

ONBUILD ADD ./build.gradle build.gradle
ONBUILD ADD ./settings.gradle settings.gradle
ONBUILD ADD ./src/build/gradle src/build/gradle

# https://stackoverflow.com/questions/21814652/how-to-download-dependencies-in-gradle/44168582#44168582
# Almost **all** dependencies, but helps separating a few before the source-code
ONBUILD RUN echo "Will execute gradle downloadDependencies"
ONBUILD RUN gradle downloadDependencies

ONBUILD ADD ./src src/

ONBUILD ARG GRADLE_BUILD_TASKS=${GRADLE_BUILD_TASKS:-build}

# Just a build and checks
ONBUILD RUN echo "Will execute gradle ${GRADLE_BUILD_TASKS}"
ONBUILD RUN gradle ${GRADLE_BUILD_TASKS}
