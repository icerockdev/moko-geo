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

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation(Deps.Libs.Android.appCompat)
    implementation(Deps.Libs.Android.playServicesLocation)

    implementation(project(":sample:mpp-library"))

    implementation(Deps.Libs.Android.activity)
    implementation(Deps.Libs.Android.fragment)
    implementation(Deps.Libs.Android.lifecycleCommon)
}
