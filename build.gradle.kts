/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */
buildscript {
    repositories {
        mavenCentral()
        google()
        gradlePluginPortal()
    }

    dependencies {
        classpath(libs.kotlinGradlePlugin)
        classpath(libs.androidGradlePlugin)
        classpath(libs.googleServicesGradlePlugin)
        classpath(libs.mokoGradlePlugin)
        classpath(libs.mobileMultiplatformGradlePlugin)
        classpath(libs.kotlinSerializationGradlePlugin)
        classpath(libs.composeJetBrainsGradlePlugin)
        classpath(libs.detektGradlePlugin)
    }
}

apply(plugin = "dev.icerock.moko.gradle.publication.nexus")
val mokoVersion = libs.versions.mokoGeoVersion.get()
allprojects {
    group = "dev.icerock.moko"
    version = mokoVersion

    // fix Reason: Task ':graphics:publishJsPublicationToOSSRHRepository' uses this output of task ':graphics:signAndroidDebugPublication' without declaring an explicit or implicit dependency. This can lead to incorrect results being produced, depending on what order the tasks are executed.
    val signingTasks = tasks.withType<Sign>()
    tasks.withType<AbstractPublishToMaven>().configureEach {
        dependsOn(signingTasks)
    }
}

tasks.register("clean", Delete::class).configure {
    delete(rootProject.buildDir)
}
