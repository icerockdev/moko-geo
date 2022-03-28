plugins {
    id("com.android.library")
    id("dev.icerock.moko.gradle.android.base")
    id("dev.icerock.moko.gradle.detekt")
    id("dev.icerock.moko.gradle.multiplatform.mobile")
    id("dev.icerock.mobile.multiplatform.android-manifest")
    id("dev.icerock.mobile.multiplatform.ios-framework")
}

dependencies {
    commonMainImplementation(libs.coroutines)

    androidMainImplementation(libs.lifecycle)
    androidMainImplementation(libs.playServicesLocation)

    commonMainApi(projects.geo)
    commonMainApi(libs.mokoMvvm)
    androidMainApi(libs.mokoMvvmLivedata)
    androidMainApi(libs.mokoMvvmDatabinding)
    commonMainApi(libs.mokoPermissions)
    commonMainApi(libs.mokoResources)
}

framework {
    export(projects.geo)
    export(libs.mokoPermissions)
}
