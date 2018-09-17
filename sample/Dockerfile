### Builder Arguments
ARG BUILDER_GIT_SHA=${GIT_SHA:-0101010}
ARG BUILDER_GIT_BRANCH=${GIT_BRANCH:-develop}
ARG BUILDER_BUILD_NUMBER=${BUILD_NUMBER:-0223}
ARG BUILDER_GRADLE_DOWNLOAD_DEPENDENCIES_CMD="gradle downloadDependencies"
ARG BUILDER_GRADLE_BUILD_CMD="gradle build -x test"

### Runner Arguments
ARG RUNNER_DIR="build/libs"
ARG RUNNER_EXTENSION="jar"
ARG RUNNER_PORT="8080"

# #####################################################################
# Build stage for building the target directory before running tests
# #####################################################################
FROM marcellodesales/springboot-builder:gradle-4.10.1-jdk-8 as marcellodesales-springboot-builder

# #####################################################################
# Build stage for running the runtime image
# #####################################################################
FROM marcellodesales/springboot-runner:openjdk-8-jre-slim

# The image depends on the built artifacts by the stage
