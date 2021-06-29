/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

plugins {
    id("multiplatform-library-convention")
    id("kotlin-parcelize")
    id("dev.icerock.mobile.multiplatform.android-manifest")
    id("publication-convention")
}

group = "dev.icerock.moko"
version = libs.versions.mokoGeoVersion.get()

dependencies {
    commonMainImplementation(libs.coroutines)

    commonMainImplementation(libs.mokoParcelize)
    commonMainImplementation(libs.mokoPermissions)

    "androidMainImplementation"(libs.appCompat)
    "androidMainImplementation"(libs.lifecycle)
    "androidMainImplementation"(libs.playServicesLocation)
}

