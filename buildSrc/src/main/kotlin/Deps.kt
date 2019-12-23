object Deps {
    object Libs {
        object Android {
            val kotlinStdLib = AndroidLibrary(
                name = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
            )
            val appCompat = AndroidLibrary(
                name = "androidx.appcompat:appcompat:${Versions.Libs.Android.appCompat}"
            )
            val material = AndroidLibrary(
                name = "com.google.android.material:material:${Versions.Libs.Android.material}"
            )
            val lifecycle = AndroidLibrary(
                name = "androidx.lifecycle:lifecycle-extensions:${Versions.Libs.Android.lifecycle}"
            )
            val playServicesLocation = AndroidLibrary(
                name = "com.google.android.gms:play-services-location:${Versions.Libs.Android.playServicesLocation}"
            )
        }

        object MultiPlatform {
            val kotlinStdLib = MultiPlatformLibrary(
                android = Android.kotlinStdLib.name,
                common = "org.jetbrains.kotlin:kotlin-stdlib-common:${Versions.kotlin}"
            )
            val coroutines = MultiPlatformLibrary(
                android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.Libs.MultiPlatform.coroutines}",
                common = "org.jetbrains.kotlinx:kotlinx-coroutines-core-common:${Versions.Libs.MultiPlatform.coroutines}",
                ios = "org.jetbrains.kotlinx:kotlinx-coroutines-core-native:${Versions.Libs.MultiPlatform.coroutines}"
            )
            val mokoGeo = MultiPlatformLibrary(
                common = "dev.icerock.moko:geo:${Versions.Libs.MultiPlatform.mokoGeo}",
                iosX64 = "dev.icerock.moko:geo-iosx64:${Versions.Libs.MultiPlatform.mokoGeo}",
                iosArm64 = "dev.icerock.moko:geo-iosarm64:${Versions.Libs.MultiPlatform.mokoGeo}"
            )
            val mokoParcelize = MultiPlatformLibrary(
                common = "dev.icerock.moko:parcelize:${Versions.Libs.MultiPlatform.mokoParcelize}",
                iosX64 = "dev.icerock.moko:parcelize-iosx64:${Versions.Libs.MultiPlatform.mokoParcelize}",
                iosArm64 = "dev.icerock.moko:parcelize-iosarm64:${Versions.Libs.MultiPlatform.mokoParcelize}"
            )
            val mokoPermissions = MultiPlatformLibrary(
                common = "dev.icerock.moko:permissions:${Versions.Libs.MultiPlatform.mokoPermissions}",
                iosX64 = "dev.icerock.moko:permissions-iosx64:${Versions.Libs.MultiPlatform.mokoPermissions}",
                iosArm64 = "dev.icerock.moko:permissions-iosarm64:${Versions.Libs.MultiPlatform.mokoPermissions}"
            )
            val mokoMvvm = MultiPlatformLibrary(
                common = "dev.icerock.moko:mvvm:${Versions.Libs.MultiPlatform.mokoMvvm}",
                iosX64 = "dev.icerock.moko:mvvm-iosx64:${Versions.Libs.MultiPlatform.mokoMvvm}",
                iosArm64 = "dev.icerock.moko:mvvm-iosarm64:${Versions.Libs.MultiPlatform.mokoMvvm}"
            )
        }
    }
}