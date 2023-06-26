plugins {
    id(Libs.Plugins.android_app) version Config.gradle_version apply false
    id(Libs.Plugins.android_library) version Config.gradle_version apply false
    id(Libs.Plugins.kotlin_android) version Libs.Kotlin.version apply false

    id(Libs.Detekt.core) version Libs.Detekt.version
    id(Libs.Dokka.core) version Libs.Dokka.version

    id(Libs.Plugins.gradle_versions) version Libs.Plugins.gradle_versions_version
}


subprojects {
    apply {
        plugin( Libs.Detekt.core )
        plugin( Libs.Dokka.core )
    }
    detekt {
        toolVersion = Libs.Detekt.version
        config = files("../config/detekt/detekt.yml")
        buildUponDefaultConfig = true
    }
}

allprojects {

    dependencies {
        detektPlugins( Libs.Detekt.formatting )
    }

}

tasks.register("clean",Delete::class){
    delete(rootProject.buildDir)
}

buildscript {
    dependencies {
        classpath(Libs.Kotlin.gradlePlugin)
    }
}

tasks.dokkaHtmlMultiModule.configure {
    outputDirectory.set(buildDir.resolve("dokkaMultiModuleOutput"))
}

// https://github.com/ben-manes/gradle-versions-plugin
tasks.withType<com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask> {
    rejectVersionIf {
        Libs.isNonStable(candidate.version)
    }
}