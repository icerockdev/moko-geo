object Deps {
    private const val kotlinVersion = "1.4.31"

    private const val androidAppCompatVersion = "1.1.0"
    private const val materialDesignVersion = "1.0.0"
    private const val androidLifecycleVersion = "2.1.0"
    private const val androidCoreTestingVersion = "2.1.0"
    private const val playServicesLocationVersion = "18.0.0"

    private const val detektVersion = "1.15.0"

    private const val coroutinesVersion = "1.4.2"
    private const val mokoParcelizeVersion = "0.6.1"
    private const val mokoPermissionsVersion = "0.8.0"
    private const val mokoMvvmVersion = "0.9.2"
    const val mokoGeoVersion = "0.3.2"

    object Android {
        const val compileSdk = 28
        const val targetSdk = 28
        const val minSdk = 16
    }

    object Plugins {
        val androidApplication = GradlePlugin(id = "com.android.application")
        val androidLibrary = GradlePlugin(id = "com.android.library")
        val kotlinJvm = GradlePlugin(id = "org.jetbrains.kotlin.jvm")
        val kotlinMultiplatform = GradlePlugin(id = "org.jetbrains.kotlin.multiplatform")
        val kotlinKapt = GradlePlugin(id = "kotlin-kapt")
        val kotlinAndroid = GradlePlugin(id = "kotlin-android")
        val kotlinAndroidExtensions = GradlePlugin(id = "kotlin-android-extensions")
        val kotlinSerialization = GradlePlugin(id = "kotlin-serialization")
        val mavenPublish = GradlePlugin(id = "org.gradle.maven-publish")
        val signing = GradlePlugin(id = "signing")

        val mobileMultiplatform = GradlePlugin(id = "dev.icerock.mobile.multiplatform")
        val iosFramework = GradlePlugin(id = "dev.icerock.mobile.multiplatform.ios-framework")

        val detekt = GradlePlugin(
            id = "io.gitlab.arturbosch.detekt",
            version = detektVersion
        )
    }

    object Libs {
        object Android {
            const val appCompat = "androidx.appcompat:appcompat:$androidAppCompatVersion"
            const val material = "com.google.android.material:material:$materialDesignVersion"
            const val lifecycle = "androidx.lifecycle:lifecycle-extensions:$androidLifecycleVersion"
            const val coroutines =
                "org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion"
            const val playServicesLocation =
                "com.google.android.gms:play-services-location:$playServicesLocationVersion"
        }

        object MultiPlatform {
            const val coroutines =
                "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion"
            const val mokoParcelize = "dev.icerock.moko:parcelize:$mokoParcelizeVersion"
            val mokoPermissions = "dev.icerock.moko:permissions:$mokoPermissionsVersion"
                .defaultMPL(ios = true)
            const val mokoMvvm = "dev.icerock.moko:mvvm:$mokoMvvmVersion"
            const val mokoGeo = "dev.icerock.moko:geo:$mokoGeoVersion"
        }

        object Tests {
            const val kotlinTestJUnit =
                "org.jetbrains.kotlin:kotlin-test-junit:$kotlinVersion"
            const val androidCoreTesting =
                "androidx.arch.core:core-testing:$androidCoreTestingVersion"
        }
    }
}
