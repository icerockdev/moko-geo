/*
 * Copyright 2023 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

plugins {
    id("dev.icerock.moko.gradle.multiplatform.mobile")
    id("dev.icerock.moko.gradle.publication")
    id("dev.icerock.moko.gradle.stub.javadoc")
    id("dev.icerock.moko.gradle.detekt")
    id("org.jetbrains.compose")
}

android {
    namespace = "dev.icerock.moko.geo.compose"

    defaultConfig {
        minSdk = 21
    }
}

dependencies {
    commonMainApi(projects.geo)
    commonMainApi(compose.runtime)

    androidMainImplementation(libs.appCompat)
    androidMainImplementation(compose.foundation)
}
