# Spring Boot Docker Images

* Builder: Builds a regular SpringBoot Image for testing or war
* Packager: Builds a regular SpringBoot Image from a Builder
* Runner: Builds a SpringBoot Image for a Runtime using WAR from builder

# Builder

* Requires the use of `downloadDependencies`
* Requires Executable WAR configured
* It will only build, and NOT generate the War
  * Generation of WAR is as follows

```dockerfile
FROM marcellodesales/spring-boot-builder as spring-boot-package-builder

RUN gradle bootRepackage
```

# Runner

* Requires the use of an intermediate stage as `spring-boot-package-builder`
* WAR must be executable

```dockerfile
FROM marcellodesales/spring-boot-package-runner
```
