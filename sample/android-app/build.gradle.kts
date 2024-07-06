plugins {
    id("dev.icerock.moko.gradle.android.application")
    id("dev.icerock.moko.gradle.detekt")
    id("kotlin-kapt")
}

android {
    namespace = "com.icerockdev.app"

    buildFeatures.dataBinding = true

    defaultConfig {

        applicationId = "dev.icerock.moko.samples.geo"

        versionCode = 1
        versionName = "0.1.0"

        minSdk = 24
    }

    compileOptions {
        sourceCompatibility(JavaVersion.VERSION_17)
        targetCompatibility(JavaVersion.VERSION_17)
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.majorVersion
    }
}

dependencies {
    implementation(libs.appCompat)
    implementation(libs.playServicesLocation)
    implementation(libs.mokoMvvmDatabinding)

    implementation(projects.sample.mppLibrary)
}
