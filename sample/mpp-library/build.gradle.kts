plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.multiplatform")
    id("dev.icerock.mobile.multiplatform")
    id("dev.icerock.mobile.multiplatform.ios-framework")
}

dependencies {
    commonMainImplementation(libs.coroutines)
    androidMainImplementation(libs.lifecycle)
    androidMainImplementation(libs.playServicesLocation)
    commonMainApi(projects.geo)
    commonMainApi(libs.mokoMvvm)
    commonMainApi(libs.mokoPermissions)

    // temporary fix of https://youtrack.jetbrains.com/issue/KT-41083
    commonMainImplementation(libs.mokoParcelize)
    commonMainImplementation(libs.mokoGraphics)
    commonMainApi(libs.mokoResources)
}

framework {
    export(project(":geo"))
    export(libs.mokoPermissions)
}
