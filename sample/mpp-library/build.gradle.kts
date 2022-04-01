plugins {
    id("dev.icerock.moko.gradle.multiplatform.mobile")
    id("dev.icerock.mobile.multiplatform.ios-framework")
    id("dev.icerock.moko.gradle.detekt")
}

dependencies {
    commonMainImplementation(libs.coroutines)

    androidMainImplementation(libs.lifecycle)
    androidMainImplementation(libs.playServicesLocation)

    commonMainApi(projects.geo)
    commonMainApi(libs.mokoMvvmCore)
    androidMainApi(libs.mokoMvvmLivedata)
    commonMainApi(libs.mokoPermissions)
    commonMainApi(libs.mokoResources)
}

framework {
    export(projects.geo)
    export(libs.mokoPermissions)
}
