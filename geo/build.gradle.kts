/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

import java.util.Base64

plugins {
    plugin(Deps.Plugins.androidLibrary)
    plugin(Deps.Plugins.kotlinMultiplatform)
    plugin(Deps.Plugins.kotlinKapt)
    plugin(Deps.Plugins.kotlinAndroidExtensions)
    plugin(Deps.Plugins.mobileMultiplatform)
    plugin(Deps.Plugins.mavenPublish)
    plugin(Deps.Plugins.signing)
}

group = "dev.icerock.moko"
version = Deps.mokoGeoVersion

dependencies {
    commonMainImplementation(Deps.Libs.MultiPlatform.coroutines)

    commonMainImplementation(Deps.Libs.MultiPlatform.mokoParcelize)
    commonMainImplementation(Deps.Libs.MultiPlatform.mokoPermissions.common)

    androidMainImplementation(Deps.Libs.Android.appCompat)
    androidMainImplementation(Deps.Libs.Android.lifecycle)
    androidMainImplementation(Deps.Libs.Android.playServicesLocation)
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
