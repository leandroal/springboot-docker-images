ARG GIT_SHA=${GIT_SHA:-0101010}
ARG GIT_BRANCH=${GIT_BRANCH:-develop}
ARG BUILD_NUMBER=${BUILD_NUMBER:-0223}
ARG GRADLE_BUILD_TASKS="build -x test"
ARG EXECUTABLE_TASK_TYPE="jar"

# #####################################################################
# Build stage for building the target directory before running tests
# #####################################################################
FROM marcellodesales/springboot-builder as marcellodesales-springboot-builder

# #####################################################################
# Build stage for running the runtime image
# #####################################################################
FROM marcellodesales/springboot-executable-runner

# The image depends on the built artifacts by the stage
# built from "marcellodesales/springboot-war-builder"
