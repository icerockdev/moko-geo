plugins {
    plugin(Deps.Plugins.androidApplication)
    plugin(Deps.Plugins.kotlinAndroid)
    plugin(Deps.Plugins.kotlinKapt)
}

android {
    compileSdkVersion(Deps.Android.compileSdk)

    buildFeatures.dataBinding = true

    dexOptions {
        javaMaxHeapSize = "2g"
    }

    defaultConfig {
        minSdkVersion(Deps.Android.minSdk)
        targetSdkVersion(Deps.Android.targetSdk)

        applicationId = "dev.icerock.moko.samples.geo"

        versionCode = 1
        versionName = "0.1.0"

        vectorDrawables.useSupportLibrary = true
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
        getByName("debug") {
            isDebuggable = true
            applicationIdSuffix = ".debug"
        }
    }

    packagingOptions {
        exclude("META-INF/*.kotlin_module")
    }
}

dependencies {
    implementation(Deps.Libs.Android.appCompat)
    implementation(Deps.Libs.Android.playServicesLocation)

    implementation(project(":sample:mpp-library"))

    implementation("androidx.activity:activity-ktx:1.2.0-rc01")
    implementation("androidx.fragment:fragment:1.3.0-rc01")

    implementation("androidx.lifecycle:lifecycle-common-java8:2.3.0-rc01")
}
