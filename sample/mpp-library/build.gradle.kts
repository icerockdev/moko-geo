plugins {
    id("com.android.library")
    id("android-base-convention")
    id("detekt-convention")
    id("org.jetbrains.kotlin.multiplatform")
    id("dev.icerock.mobile.multiplatform.android-manifest")
    id("dev.icerock.mobile.multiplatform.ios-framework")
}

kotlin {
    android()
    ios()
}

dependencies {
    commonMainImplementation(libs.coroutines)
    "androidMainImplementation"(libs.lifecycle)
    "androidMainImplementation"(libs.playServicesLocation)
    commonMainApi(projects.geo)
    commonMainApi(libs.mokoMvvm)
    commonMainApi(libs.mokoPermissions)

    // temporary fix of https://youtrack.jetbrains.com/issue/KT-41083
    commonMainImplementation(libs.mokoParcelize)
    commonMainImplementation(libs.mokoGraphics)
    commonMainApi(libs.mokoResources)
}

framework {
    export(projects.geo)
    export(libs.mokoPermissions)
}
