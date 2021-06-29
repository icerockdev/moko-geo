plugins {
    id("android-app-convention")
    id("kotlin-android")
    id("kotlin-kapt")
}

android {
    buildFeatures.dataBinding = true

    defaultConfig {

        applicationId = "dev.icerock.moko.samples.geo"

        versionCode = 1
        versionName = "0.1.0"
    }
}

dependencies {
    implementation(libs.appCompat)
    implementation(libs.playServicesLocation)

    implementation(projects.sample.mppLibrary)
}
