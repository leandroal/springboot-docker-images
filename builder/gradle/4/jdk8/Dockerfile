# #####################################################################
# Build and pull dependencies
# #####################################################################
FROM gradle:4.10.1-jdk8
MAINTAINER marcello.desales@gmail.com
USER root

# Build Arguments to be received from a build pipeline
# Use --build-arg <varname>=<value> during docker build
ONBUILD ARG GIT_SHA=${GIT_SHA:-0000000}
ONBUILD ARG GIT_BRANCH=${GIT_BRANCH:-develop}
ONBUILD ARG BUILD_NUMBER=${BUILD_NUMBER:-0}

# * Github at https://github.intuit.com/services-config/config-service/commit/${GIT_SHA}
# * Jenkins at https://build2.intuit.com/services-config/blue/organizations/jenkins/config-service/detail/${GIT_BRANCH/${BUILD_NUMBER}
ONBUILD ENV BUILD_TAG sha:${GIT_SHA},build:${GIT_BRANCH}/${BUILD_NUMBER}
ONBUILD ENV GIT_SHA=${GIT_SHA} GIT_BRANCH=${GIT_BRANCH}

WORKDIR /app/

ONBUILD ADD ./build.gradle build.gradle
ONBUILD ADD ./settings.gradle settings.gradle
ONBUILD ADD ./src/build/gradle src/build/gradle

# https://stackoverflow.com/questions/21814652/how-to-download-dependencies-in-gradle/44168582#44168582
# Almost **all** dependencies, but helps separating a few before the source-code
ONBUILD RUN gradle downloadDependencies

ONBUILD ADD ./src src/

# Just a build and checks
ONBUILD RUN gradle build -x war -x test
