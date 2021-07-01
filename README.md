![moko-mvvm](https://user-images.githubusercontent.com/5010169/71337878-0e0d0f80-2580-11ea-8ac5-69a132334960.png)  
[![GitHub license](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0) [![Download](https://img.shields.io/maven-central/v/dev.icerock.moko/geo) ](https://repo1.maven.org/maven2/dev/icerock/moko/geo) ![kotlin-version](https://kotlin-version.aws.icerock.dev/kotlin-version?group=dev.icerock.moko&name=geo)

# Mobile Kotlin geolocation module
This is a Kotlin Multiplatform library that provides geolocation to common code.

## Table of Contents
- [Features](#features)
- [Requirements](#requirements)
- [Installation](#installation)
- [Usage](#usage)
- [Samples](#samples)
- [Set Up Locally](#set-up-locally)
- [Contributing](#contributing)
- [License](#license)

## Features
- **Geolocation tracking** - track user geolocation from common code;

## Requirements
- Gradle version 6.0+
- Android API 16+
- iOS version 9.0+

## Installation
root build.gradle  
```groovy
allprojects {
    repositories {
        mavenCentral()
    }
}
```

project build.gradle
```groovy
dependencies {
    commonMainApi("dev.icerock.moko:geo:0.4.0")
    androidMainImplementation("com.google.android.gms:play-services-location:18.0.0")
}
```

## Usage
in common code:
```kotlin
class TrackerViewModel(
    val locationTracker: LocationTracker
) : ViewModel() {

    init {
        viewModelScope.launch {
            locationTracker.getLocationsFlow()
                .distinctUntilChanged()
                .collect { println("new location: $it") }
        }
    }

    fun onStartPressed() {
        viewModelScope.launch { locationTracker.startTracking() }
    }

    fun onStopPressed() {
        locationTracker.stopTracking()
    }
}
```

In Android:
```kotlin
// create ViewModel
val locationTracker = LocationTracker(
    permissionsController = PermissionsController(applicationContext = applicationContext)
)
val viewModel = TrackerViewModel(locationTracker)

// bind tracker to lifecycle
viewModel.locationTracker.bind(lifecycle, this, supportFragmentManager)
```

In iOS:
```swift
let viewModel = TrackerViewModel(
    locationTracker: LocationTracker(
        permissionsController: PermissionsController(),
        accuracy: kCLLocationAccuracyBest
    )
)
```

## Samples
Please see more examples in the [sample directory](sample).

## Set Up Locally 
- The [geo directory](geo) contains the `geo` library;
- In [sample directory](sample) contains sample apps for Android and iOS; plus the mpp-library connected to the apps.

## Contributing
All development (both new features and bug fixes) is performed in the `develop` branch. This way `master` always contains the sources of the most recently released version. Please send PRs with bug fixes to the `develop` branch. Documentation fixes in the markdown files are an exception to this rule. They are updated directly in `master`.

The `develop` branch is pushed to `master` on release.

For more details on contributing please see the [contributing guide](CONTRIBUTING.md).

## License
        
    Copyright 2019 IceRock MAG Inc.
    
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    
       http://www.apache.org/licenses/LICENSE-2.0
    
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
