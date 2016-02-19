# RxBus: a Java EventBus using ReactiveX

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

## Usage

### Create an RxBus instance

The default implementation is called `DefaultRxBus`:

```java
RxBus rxBus = new DefaultRxBus();
```

The `DefaultRxBus` uses a serialized `PublishSubject`, but you can also provide your own `Subject`
implementation:

```java
RxBus rxBus = new DefaultRxBus(BehaviorSubject.create());
```

### Emit (dispatch / post) an event

```java
rxBus.emit(new MyEvent());
```

Aliases: `RxBus#dispatch()`, `RxBus#post()`.

### Subscribing to events

You can subscribe to all events that are posted on the RxBus:

```java
rxBus.subscribe(observer);
```

But you're probably only interested in events of a certain type:

```java
rxBus.subscribe(MyEvent.class, observer);
```

`RxBus#subscribe()` copies the `Observable#subscribe()` methods and, likewise, returns a
`Subscription` reference.

### Observing event streams

Directly subscribing to events is nice, but the cool thing about Rx is its operators! To get an
`Observable` instance, use `RxBus.observe()`.

You can observe all events that are posted on the RxBus:

```java
rxBus.observe().subscribe(observer);
```

But you're probably only interested in events of a certain type:

```java
rxBus.observe(MyEvent.class).subscribe(observer);
```

Alias: `RxBus#on()`.

### 'Forwarding' observable streams to events

A common pattern when working with an EventBus is observing one stream and emitting 'global' events
in response. This could be achieved as follows:

```java
Observable<String> exampleStream = Observable.just("example");

exampleStream.map(new Func1<String, ExampleStringEvent>() {
    @Override
    public ExampleStringEvent call(String value) {
        return new ExampleStringEvent(value);
    }
}).subscribe(new Action1<ExampleStringEvent>() {
    @Override
    public void call(ExampleStringEvent event) {
        rxBus.emit(event);
    }
});
```

To apply this pattern more comfortably, RxBus provides the `RxBus#forward()` methods:

```java
rxBus.forward(exampleStream, new Func1<String, ExampleStringEvent>() {
    @Override
    public ExampleStringEvent call(String value) {
        return new ExampleStringEvent(value);
    }
});
```

This becomes even more readable when using a lambda:

```java
rxBus.forward(exampleStream, value -> new ExampleStringEvent(value));
```

Or even a method reference:

```java
rxBus.forward(exampleStream, ExampleStringEvent::new);
```

## Copyright and license

Code and documentation copyright 2016 Cooking Fox. Code released under the [MIT license](LICENSE).
