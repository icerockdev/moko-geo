/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

import java.util.Base64

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.multiplatform")
    id("kotlin-kapt")
    id("kotlin-android-extensions")
    id("dev.icerock.mobile.multiplatform")
    id("org.gradle.maven-publish")
    id("signing")
}

group = "dev.icerock.moko"
version = libs.versions.mokoGeoVersion.get()

dependencies {
    commonMainImplementation(libs.coroutines)

    commonMainImplementation(libs.mokoParcelize)
    commonMainImplementation(libs.mokoPermissions)

    androidMainImplementation(libs.appCompat)
    androidMainImplementation(libs.lifecycle)
    androidMainImplementation(libs.playServicesLocation)
}

val javadocJar by tasks.registering(Jar::class) {
    archiveClassifier.set("javadoc")
}

publishing {
    repositories.maven("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/") {
        name = "OSSRH"

        credentials {
            username = System.getenv("OSSRH_USER")
            password = System.getenv("OSSRH_KEY")
        }
    }

    publications.withType<MavenPublication> {
        // Stub javadoc.jar artifact
        artifact(javadocJar.get())

        // Provide artifacts information requited by Maven Central
        pom {
            name.set("MOKO geo")
            description.set("Geolocation access for mobile (android & ios) Kotlin Multiplatform development")
            url.set("https://github.com/icerockdev/moko-geo")
            licenses {
                license {
                    url.set("https://github.com/icerockdev/moko-geo/blob/master/LICENSE.md")
                }
            }

            developers {
                developer {
                    id.set("Alex009")
                    name.set("Aleksey Mikhailov")
                    email.set("aleksey.mikhailov@icerockdev.com")
                }
            }

            scm {
                connection.set("scm:git:ssh://github.com/icerockdev/moko-geo.git")
                developerConnection.set("scm:git:ssh://github.com/icerockdev/moko-geo.git")
                url.set("https://github.com/icerockdev/moko-geo")
            }
        }
    }
}

signing {
    val signingKeyId: String? = System.getenv("SIGNING_KEY_ID")
    val signingPassword: String? = System.getenv("SIGNING_PASSWORD")
    val signingKey: String? = System.getenv("SIGNING_KEY")?.let { base64Key ->
        String(Base64.getDecoder().decode(base64Key))
    }
    if (signingKeyId != null) {
        useInMemoryPgpKeys(signingKeyId, signingKey, signingPassword)
        sign(publishing.publications)
    }
}
