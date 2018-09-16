# Running

## BootRun

* Uses `build.gradle`
* Run with `bootRun` default profile

```
gradle bootRun
```

* Run with `bootRun` with `dev` profile

```
SPRING_PROFILES_ACTIVE=dev gradle bootRun
```

## Docker Container

* Uses the `Dockerfile`
* Build a Docker Image default arguments

```
$ docker build -t myapp .
Sending build context to Docker daemon  20.23MB
Step 1/7 : ARG GIT_SHA=${GIT_SHA:-0101010}
Step 2/7 : ARG GIT_BRANCH=${GIT_BRANCH:-develop}
Step 3/7 : ARG BUILD_NUMBER=${BUILD_NUMBER:-0223}
Step 4/7 : ARG GRADLE_BUILD_TASKS="build -x test"
Step 5/7 : ARG EXECUTABLE_TASK_TYPE="jar"
Step 6/7 : FROM marcellodesales/springboot-builder as marcellodesales-springboot-builder
 ---> Using cache
 ---> Using cache
 ---> Using cache
 ---> Using cache
 ---> Using cache
 ---> Using cache
 ---> Running in 6b7c60c82fa1
Will execute gradle downloadDependencies
Removing intermediate container 6b7c60c82fa1
 ---> Running in 44f6a573e60e

Welcome to Gradle 4.10.1!

Here are the highlights of this release:
 - Incremental Java compilation by default
 - Periodic Gradle caches cleanup
 - Gradle Kotlin DSL 1.0-RC6
 - Nested included builds
 - SNAPSHOT plugin versions in the `plugins {}` block

For more details see https://docs.gradle.org/4.10.1/release-notes.html

Starting a Gradle Daemon (subsequent builds will be faster)

> Task :downloadDependencies

Retrieving dependencies for annotationProcessor

Retrieving dependencies for apiElements

Retrieving dependencies for archives

Retrieving dependencies for bootArchives

Retrieving dependencies for compile

Retrieving dependencies for compileClasspath

Retrieving dependencies for compileOnly

Retrieving dependencies for default

Retrieving dependencies for implementation

Retrieving dependencies for runtime

Retrieving dependencies for runtimeClasspath

Retrieving dependencies for runtimeElements

Retrieving dependencies for runtimeOnly

Retrieving dependencies for testAnnotationProcessor

Retrieving dependencies for testCompile

Retrieving dependencies for testCompileClasspath

Retrieving dependencies for testCompileOnly

Retrieving dependencies for testImplementation

Retrieving dependencies for testRuntime

Retrieving dependencies for testRuntimeClasspath

Retrieving dependencies for testRuntimeOnly

BUILD SUCCESSFUL in 14s
1 actionable task: 1 executed
Removing intermediate container 44f6a573e60e
 ---> Running in 40a596327554
Will execute gradle build -x test
Removing intermediate container 40a596327554
 ---> Running in 8642c9c067f4

Welcome to Gradle 4.10.1!

Here are the highlights of this release:
 - Incremental Java compilation by default
 - Periodic Gradle caches cleanup
 - Gradle Kotlin DSL 1.0-RC6
 - Nested included builds
 - SNAPSHOT plugin versions in the `plugins {}` block

For more details see https://docs.gradle.org/4.10.1/release-notes.html

Starting a Gradle Daemon (subsequent builds will be faster)
> Task :compileJava
> Task :processResources
> Task :classes
> Task :bootJar
> Task :jar SKIPPED
> Task :assemble
> Task :check
> Task :build

BUILD SUCCESSFUL in 20s
3 actionable tasks: 3 executed
Removing intermediate container 8642c9c067f4
 ---> 405621119bcf
Step 7/7 : FROM marcellodesales/springboot-executable-runner
 ---> Using cache
 ---> Using cache
 ---> Using cache
 ---> Running in 46cb7cebdf3b
-rw-r--r-- 1 root root 16141378 Sep 16 23:16 /tmp/myapp-0.1.0.jar
Removing intermediate container 46cb7cebdf3b
 ---> Running in b47bfaf3a78d
Will copy from /app/src/main/resources /runtime/resources
Removing intermediate container b47bfaf3a78d
 ---> Running in cc5b00fe7dad
total 20
drwxr-xr-x 2 root root 4096 Sep 16 23:17 .
drwxr-xr-x 1 root root 4096 Sep 16 23:17 ..
-rw-r--r-- 1 root root   48 Sep 16 23:12 application-dev.yml
-rw-r--r-- 1 root root   47 Sep 16 23:12 application-prd.yml
-rw-r--r-- 1 root root   51 Sep 16 23:12 application.yml
Removing intermediate container cc5b00fe7dad
 ---> Running in 40a70b1e8304
Renaming the executable jar to the runtime dir
Removing intermediate container 40a70b1e8304
 ---> Running in 3d3eea4bdd5c
Removing intermediate container 3d3eea4bdd5c
 ---> 89923a61a08c
Successfully built 89923a61a08c
Successfully tagged myapp:latest
```

* Running in the `dev` profile
  * Loads the file `application-dev.yml`
  * The logs will be showing `The following profiles are active: dev`

```
$ docker run -ti -e SPRING_PROFILES_ACTIVE=dev -p 8080:8080 myapp

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::        (v2.0.3.RELEASE)

2018-09-16 23:17:50.719  INFO 9 --- [           main] hello.Application                        : Starting Application on 44fb9b4347d6 with PID 9 (/runtime/server.jar started by root in /runtime)
2018-09-16 23:17:50.725  INFO 9 --- [           main] hello.Application                        : The following profiles are active: dev
2018-09-16 23:17:50.820  INFO 9 --- [           main] ConfigServletWebServerApplicationContext : Refreshing org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext@6833ce2c: startup date [Sun Sep 16 23:17:50 UTC 2018]; root of context hierarchy
2018-09-16 23:17:52.445  INFO 9 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 8080 (http)
```

## Validating

```
curl localhost:8080
Hello Docker World from My App: Environment for development%
```

## Docker Build Args

```
docker build -t myapp --build-arg GIT_SHA=$(git rev-parse HEAD) \
         --build-arg GIT_BRANCH=$(git rev-parse --abbrev-ref --symbolic HEAD) \
         --build-arg BUILD_NUMBER=1234 .
```
