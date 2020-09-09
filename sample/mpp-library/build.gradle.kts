plugins {
    plugin(Deps.Plugins.androidLibrary)
    plugin(Deps.Plugins.kotlinMultiplatform)
    plugin(Deps.Plugins.mobileMultiplatform)
    plugin(Deps.Plugins.iosFramework)
}

dependencies {
    commonMainImplementation(Deps.Libs.MultiPlatform.coroutines)

    androidMainImplementation(Deps.Libs.Android.lifecycle)
    androidMainImplementation(Deps.Libs.Android.playServicesLocation)

    commonMainApi(Deps.Libs.MultiPlatform.mokoGeo)
    commonMainApi(Deps.Libs.MultiPlatform.mokoMvvm)
    commonMainApi(Deps.Libs.MultiPlatform.mokoPermissions.common)

    // temporary fix of https://youtrack.jetbrains.com/issue/KT-41083
    commonMainImplementation("dev.icerock.moko:parcelize:0.4.0")
    commonMainImplementation("dev.icerock.moko:graphics:0.4.0")
    commonMainApi("dev.icerock.moko:resources:0.13.0")
}

framework {
    export(project(":geo"))
    export(Deps.Libs.MultiPlatform.mokoPermissions)
}
