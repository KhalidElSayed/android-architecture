
# TODO-MVVM-RXJAVA

### Summary

Sample Android app that I use as a reference for new Android projects. It demonstrates the architecture, tools and guidelines that I use when developing for the Android platform.

This sample demonstrates a basic [Model-View-ViewModel](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93viewmode) (MVVM) architecture and provides a foundation on which the other samples and projects are built. This sample uses [RxJava 2](https://github.com/ReactiveX/RxJava) to implement concurrency, and abstract the presentation and data layers.

This sample is based on the TODO-MVP-RXJAVA project and uses RxJava 2 for communication between the data model and ViewModels layers.

### Dependencies
Libraries and tools included:

* [RxJava](https://github.com/ReactiveX/RxJava)
* [RxAndroid](https://github.com/ReactiveX/RxAndroid)
* [Dagger 2](http://google.github.io/dagger/)
* [Retrofit 2](http://square.github.io/retrofit/)
* [Room](https://developer.android.com/topic/libraries/architecture/room)
* [Butterknife](https://github.com/JakeWharton/butterknife)
* [androidx libraries](https://developer.android.com/jetpack/androidx)
* [Timber](https://github.com/JakeWharton/timber)
* [Robolectric](http://robolectric.org/)
* [Mockito](http://mockito.org/)

## Requirements

* JDK 1.8
* [Android SDK](http://developer.android.com/sdk/index.html).
* Android 10 [(API 29)](http://developer.android.com/tools/revisions/platforms.html).
* Latest Android SDK Tools and build tools.

## Architecture

This project follows architecture guidelines that are based on [MVVM (Model View ViewModel)](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93viewmode).

## Features

### Complexity - understandability

#### Use of architectural frameworks/libraries/tools:

Building an app with RxJava is not trivial as it uses new concepts.

#### Conceptual complexity

Developers need to be familiar with RxJava, which is not trivial.

### Testability

#### Unit testing

Very High. Given that the RxJava ``Observable``s are highly unit testable, unit tests are easy to implement.

### Maintainability

#### Ease of amending or adding a feature

High.

#### Learning cost

Medium as RxJava is not trivial.

## Original owner

[Florina Muntenescu](https://github.com/florina-muntenescu)

## License

```
    Copyright 2019 Khalid ElSayed.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
```
