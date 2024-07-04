plugins {
    id("dev.icerock.moko.gradle.multiplatform.mobile")
    id("dev.icerock.mobile.multiplatform.ios-framework")
    id("dev.icerock.moko.gradle.detekt")
}

android {
    namespace = "com.icerockdev.library"

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

    androidMainImplementation(libs.lifecycle)
    androidMainImplementation(libs.playServicesLocation)

    commonMainApi(projects.geo)
    commonMainApi(libs.mokoMvvmCore)
    commonMainApi(libs.mokoMvvmLivedata)
    commonMainApi(libs.mokoPermissions)
    commonMainApi(libs.mokoResources)
}

framework {
    export(projects.geo)
    export(libs.mokoPermissions)
}
