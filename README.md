# Spring Boot Docker Images

* `marcellodesales/springboot-builder`: Base Docker Image with Gradle that builds a `SpringBoot` project locally.
  * `marcellodesales/springboot-war-builder`: Intermediate stage to generate an executable `WAR` for the app.
* `marcellodesales/springboot-war-runner`: Docker Image to run an executable WAR from a Gradle project built by the builder.

# Description

## Builder

Implementation of a set of `ONBUILD` instructions that are reusable for any SpringBoot Application:

* Injection of env vars such as `GIT_SHA`, `GIT_BRANCH` and `BUILD_NUMBER` (Jenkins) used for versioning.
* Builds the project using the build tool used.
* Executes `gradle build -x test` as a base build
  * You can use multiple stages to generate test images with this.

## Runner

Implementation of all tasks to execute a runnable `WAR` from a SpringBoot application.

* Copies the WAR file to the appropriate directory.
* Prepares the image with underlying capabilities to make TLS calls.
* Uses small JRE images.

# Gradle

* Gradle version is latest, currently at 4.10.1

* Uses Gradle as the builder tool and requires:
  * `build.gradle`: Main build file
  * `settings.gradle`: Project settings
  * `src/build/gradle`: Individual tasks implementation linked at `build.gradle`
* Regular Java project using maven structure
  * `src/main/java`: java project

## Gradle Tasks Required

Requires the use of regular tasks for the following:

* All SpringBoot Java tasks such as `build`, `test`, etc.
* `downloadDependencies`
  * https://stackoverflow.com/questions/21814652/how-to-download-dependencies-in-gradle/44168582#44168582
* `bootRepackage`
  * https://docs.spring.io/spring-boot/docs/1.5.16.RELEASE/reference/html/build-tool-plugins-gradle-plugin.html#build-tool-plugins-gradle-packaging

## Gradle Builder Example

* The builder and the war builder can be used together to generate the WAR

```dockerfile
FROM marcellodesales/springboot-builder as marcellodesales/springboot-war-builder

RUN gradle bootRepackage
```

## Gradle Runner Example

* Requires the use of an intermediate stage named `marcellodesales/springboot-war-builder`
  * This stage must have created a WAR under `/app/build/libs/*.war`

```dockerfile
FROM marcellodesales/springboot-war-runner
```

After this builds, you will have an executable image:

```
docker build -t myapp .
docker run -ti myapp
```
