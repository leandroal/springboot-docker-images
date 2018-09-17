# Spring Boot Docker Images

* `marcellodesales/springboot-builder`: Base Docker Image with Gradle that builds a `SpringBoot` project locally.
  * https://hub.docker.com/r/marcellodesales/springboot-builder
  * `marcellodesales-springboot-builder`: Intermediate stage to generate an executable `JAR` or `WAR` for the app.
* `marcellodesales/springboot-runner`: Docker Image to run an executable WAR from a Gradle project built by the builder.
  * https://hub.docker.com/r/marcellodesales/springboot-runner

# Description

## Builder

Implementation of a set of `ONBUILD` instructions that are reusable for any SpringBoot Application:

* Injection of env vars such as `GIT_SHA`, `GIT_BRANCH` and `BUILD_NUMBER` for versioning.
* Supports the following builders:
  * [x] Gradle 4.x.x + openjdk 1.8
  * [x] Maven 3.x.x + openjdk 1.8
  * [ ] Gradle 4.x.x + openjdk 1.10
  * [ ] Maven 3.x.x + openjdk 1.10

## Runner

Implementation of all tasks to execute a runnable `WAR` from a SpringBoot application.

* Uses the built executable file to the appropriate directory.
* Prepares the image with underlying capabilities to make TLS calls.
* Uses small JRE images.
* Supports the following Runners:
  * [x] JRE Slim 1.8
  * [ ] JRE Slim 1.10

# Gradle Builder

* Gradle version is latest, currently at 4.10.1 with OpenJDK 1.8

* Uses Gradle as the builder tool and requires:
  * `build.gradle`: Main build file
  * `settings.gradle`: Project settings
  * `src/build/gradle`: Individual tasks implementation linked at `build.gradle`
* Regular Java project using maven structure
  * `src/main/java`: java project

## Gradle Builder Args

Requires the use of the following build args

* `BUILDER_GIT_SHA`: the current sha of the project 
* `BUILDER_GIT_BRANCH`: the current branch of the project
* `BUILDER_BUILD_NUMBER`: the current builder number of CI Server
* `BUILDER_GRADLE_DOWNLOAD_DEPENDENCIES_CMD`: the intermediate step to pull dependencies from the build
  * `downloadDependencies` can be implemented
  * https://stackoverflow.com/questions/21814652/how-to-download-dependencies-in-gradle/44168582#44168582
* `BUILDER_GRADLE_BUILD_CMD`: the command used to build the executable `jar` or `war`
  * `gradle bootRepackage` or `gradle build`
  * https://docs.spring.io/spring-boot/docs/1.5.16.RELEASE/reference/html/build-tool-plugins-gradle-plugin.html#build-tool-plugins-gradle-packaging

## Gradle Builder Dockerfile

* The builder should be the first image of the Dockerfile
  * It can also include additional steps required by the builder such as installing new dependencies, etc.

```dockerfile
FROM marcellodesales/springboot-builder:gradle-4.10.1-jdk-8 as marcellodesales-springboot-builder

RUN echo "You can add extra packages or anything needed to the final image for building"
```

# Maven Builder

* Maven version is 3.2.5 with OpenJDK 1.8
* Uses Maven as the builder tool and requires:
  * `pom.xml`: Main build file
  * `settings.xml`: Project settings
  * `src/build/gradle`: Individual tasks implementation linked at `build.gradle`
* Regular Java project using maven structure
  * `src/main/java`: java project

## Maven Builder Args

Requires the use of the following build args

* `BUILDER_GIT_SHA`: the current sha of the project 
* `BUILDER_GIT_BRANCH`: the current branch of the project
* `BUILDER_BUILD_NUMBER`: the current builder number of CI Server
* `BUILDER_MAVEN_DOWNLOAD_DEPENDENCIES_CMD`: the intermediate step to pull dependencies from the build
  * `maven-dependency-plugin:3.0.2:go-offline` can be used
  * https://maven.apache.org/plugins/maven-dependency-plugin/go-offline-mojo.html
* `BUILDER_MAVEN_BUILD_CMD`: the command used to build the executable `jar` or `war`
  * `mvn -s settings.xml install -P embedded`

# Generic Runner

* The runner image is the same for any builder supported
  * Gradle
  * Maven
* The single image simplifies the whole process and requires a couple of args

## Runner Args

Requires the use of the following build args

* `RUNNER_DIR`: The directory containing the executable file.
  * Default Gradle directory is `build/libs`
  * Default Maven directory is `target`
* `RUNNER_EXTENSION`: the extension of the executable binary
  * Gradle has plenty of examples with `jar` and `war` files.
* `RUNNER_PORT`: the port to use in the `EXPOSE` instruction in the final Docker Image

This runnable image will require any stage of the Dockerfile to be named `as marcellodesales-springboot-builder` as shown above.

## Runner Dockerfile

* The runner is the last image in the `Dockerfile` without any stage name.
  * It also should be specified the tag referring to the supported runtime.
* The version supported is the tag with the kind of JRE to use.

```dockerfile
...
...

FROM marcellodesales/springboot-runner:openjdk-8-jre-slim

RUN echo "You can include more libraries or anything in the Runnable image"
```

# Gradle Sample

* Provide the build args as needed. 
* You can use the default images.
  * Runnable supports calling TLS services (certs)
  * See the samples for details
  * See the runner Dockerfile for details

```dockerfile
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

FROM marcellodesales/springboot-builder:gradle-4.10.1-jdk-8 as marcellodesales-springboot-builder

FROM marcellodesales/springboot-runner:openjdk-8-jre-slim
```

Go to the directory `sample` directory for more details.

# Contributing

* Send a PR
