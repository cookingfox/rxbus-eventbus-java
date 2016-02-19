# rxbus-eventbus-java
A simple EventBus using RX Java

[![Build Status](https://travis-ci.org/cookingfox/rxbus-eventbus-java.svg?branch=master)](https://travis-ci.org/cookingfox/rxbus-eventbus-java)

## Download

[![Download](https://api.bintray.com/packages/cookingfox/maven/rxbus-eventbus-java/images/download.svg)](https://bintray.com/cookingfox/maven/rxbus-eventbus-java/_latestVersion)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.cookingfox/rxbus-eventbus-java/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.cookingfox/rxbus-eventbus-java)

The distribution is hosted on [Bintray](https://bintray.com/cookingfox/maven/rxbus-eventbus-java/view).
To include the package in your projects, you can add the jCenter repository.

### Gradle

Add jCenter to your `repositories` block:

```groovy
repositories {
    jcenter()
}
```

and add the project to the `dependencies` block in your `build.gradle`:

```groovy
dependencies {
    compile 'com.cookingfox:rxbus-eventbus-java:0.1.1'
}
```

### Maven

Add jCenter to your repositories in `pom.xml` or `settings.xml`:

```xml
<repositories>
    <repository>
        <id>jcenter</id>
        <url>http://jcenter.bintray.com</url>
    </repository>
</repositories>
```

and add the project declaration to your `pom.xml`:

```xml
<dependency>
    <groupId>com.cookingfox</groupId>
    <artifactId>rxbus-eventbus-java</artifactId>
    <version>0.1.1</version>
</dependency>
```
