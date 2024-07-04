/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

plugins {
    id("dev.icerock.moko.gradle.multiplatform.mobile")
    id("dev.icerock.moko.gradle.publication")
    id("dev.icerock.moko.gradle.stub.javadoc")
    id("dev.icerock.moko.gradle.detekt")
    id("kotlin-parcelize")
}

android {
    namespace = "dev.icerock.moko.geo"

    compileOptions {
        sourceCompatibility(JavaVersion.VERSION_17)
        targetCompatibility(JavaVersion.VERSION_17)
    }
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = JavaVersion.VERSION_17.majorVersion
            }
        }
    }
}

dependencies {
    commonMainImplementation(libs.coroutines)

    commonMainApi(libs.mokoParcelize)
    commonMainApi(libs.mokoPermissions)

    androidMainImplementation(libs.appCompat)
    androidMainApi(libs.lifecycle)
    androidMainApi(libs.playServicesLocation)
}

