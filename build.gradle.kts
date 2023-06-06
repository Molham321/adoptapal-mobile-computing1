plugins {
    id(Libs.Plugins.android_app) version Config.gradle_version apply false
    id(Libs.Plugins.android_library) version Config.gradle_version apply false
    id(Libs.Plugins.kotlin_android) version Libs.Kotlin.version apply false

    id(Libs.Detekt.core) version Libs.Detekt.version
    id(Libs.Dokka.core) version Libs.Dokka.version

    id(Libs.Plugins.gradle_versions) version Libs.Plugins.gradle_versions_version
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

buildscript {
    dependencies {
        classpath(Libs.Kotlin.gradlePlugin)
    }
}