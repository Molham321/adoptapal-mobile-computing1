import java.util.Locale

object Libs {

    object Plugins {
        const val android_app = "com.android.application"
        const val android_library = "com.android.library"
        const val kotlin_android = "org.jetbrains.kotlin.android"
        const val kotlin_kapt = "kotlin-kapt"
        const val java_library = "java-library"
        const val kotlin_jvm = "org.jetbrains.kotlin.jvm"

        const val gradle_versions_version = "0.46.0"
        const val gradle_versions = "com.github.ben-manes.versions"

        const val kotlin_serialization = "plugin.serialization"
        const val kotlin_serialization_version = "1.6.21"

    }

    object Kotlin {
        const val version = "1.8.20"
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$version"
        const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$version"
        const val extensions = "org.jetbrains.kotlin:kotlin-android-extensions:$version"

    }

    object Coroutines {
        private const val version = "1.6.4"
        const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
        const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
        const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$version"
    }

    object AndroidX {
        const val coreKtx = "androidx.core:core-ktx:1.10.0"

        object Activity {
            private const val version = "1.7.0"
            const val activityCompose = "androidx.activity:activity-compose:$version"
        }

        object Compose {
            const val version = "1.4.1"
            const val compiler_version = "1.4.5"

            const val ui = "androidx.compose.ui:ui:$version"
            const val foundation = "androidx.compose.foundation:foundation:$version"
            const val layout = "androidx.compose.foundation:foundation-layout:$version"
            const val material = "androidx.compose.material:material:$version"
            const val materialIconsExtended =
                "androidx.compose.material:material-icons-extended:$version"
            const val runtime = "androidx.compose.runtime:runtime:$version"
            const val runtimeLivedata = "androidx.compose.runtime:runtime-livedata:$version"
            const val tooling = "androidx.compose.ui:ui-tooling:$version"
            const val toolingPreview = "androidx.compose.ui:ui-tooling-preview:$version"
            const val test = "androidx.compose.ui:ui-test:$version"
            const val uiTest = "androidx.compose.ui:ui-test-junit4:$version"
            const val uiText = "androidx.compose.ui:ui-text-google-fonts:$version"
            const val uiTestManifest = "androidx.compose.ui:ui-test-manifest:$version"
            const val uiUtil = "androidx.compose.ui:ui-util:${version}"
            const val viewBinding = "androidx.compose.ui:ui-viewbinding:$version"
        }

        object Espresso {
            private const val version = "3.5.1"
            const val core = "androidx.test.espresso:espresso-core:$version"
        }

        object Navigation {
            private const val version = "2.5.3"
            const val fragment = "androidx.navigation:navigation-fragment-ktx:$version"
            const val ui = "androidx.navigation:navigation-ui-ktx:$version"
            const val dynamicFeature =
                "androidx.navigation:navigation-dynamic-features-fragment:$version"
            const val test = "androidx.navigation:navigation-testing:$version"
            const val compose = "androidx.navigation:navigation-compose:$version"
        }

        object Lifecycle {
            private const val version = "2.6.1"
            const val runtime = "androidx.lifecycle:lifecycle-runtime-ktx:$version"
            const val extensions = "androidx.lifecycle:lifecycle-extensions:$version"
            const val livedata = "androidx.lifecycle:lifecycle-livedata-ktx:$version"
            const val viewmodel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$version"
            const val viewModelCompose = "androidx.lifecycle:lifecycle-viewmodel-compose:$version"
        }

        object Maps {
            private const val maps_compose_verion = "2.2.1"
            private const val play_services_version = "18.1.0"
            const val core = "com.google.maps.android:maps-compose:$maps_compose_verion"
            const val play_services =
                "com.google.android.gms:play-services-maps:$play_services_version"
            const val location = "com.google.android.gms:play-services-location:19.0.1"
        }

        object MaterialDesign {
            private const val version = "1.9.0"
            const val core = "com.google.android.material:material:$version"
        }
    }

    // https://developer.android.com/jetpack/androidx/releases/room
    object Room {
        private const val version = "2.5.1"
        const val runtime = "androidx.room:room-runtime:$version"
        const val compiler = "androidx.room:room-compiler:$version"
        const val ktx = "androidx.room:room-ktx:$version"
        const val testing = "androidx.room:room-testing:$version"
    }

    // https://github.com/JakeWharton/timber
    object Timber {
        private const val version = "5.0.1"
        const val core = "com.jakewharton.timber:timber:$version"
    }

    object DataStore {
        private const val version = "1.0.0"
        const val datastore_pref = "androidx.datastore:datastore-preferences:$version"
        const val datastore_typed = "androidx.datastore:datastore:$version"
    }

    // https://github.com/Kotlin/dokka
    object Dokka {
        const val version = "1.8.10"
        const val core = "org.jetbrains.dokka"
    }

    // https://github.com/detekt/detekt
    object Detekt {
        const val version = "1.20.0"
        const val core = "io.gitlab.arturbosch.detekt"
        const val formatting = "io.gitlab.arturbosch.detekt:detekt-formatting:$version"
    }

    // https://insert-koin.io
    object Koin {
        private const val version = "3.4.0"
        const val core = "io.insert-koin:koin-core:${version}"
        const val android = "io.insert-koin:koin-android:${version}"
        const val compose = "io.insert-koin:koin-androidx-compose:${version}"
        const val navigation = "io.insert-koin:koin-androidx-navigation:${version}"
        const val test = "io.insert-koin:koin-test:${version}"
    }

    // https://github.com/square/moshi/
    object Moshi {
        private const val version = "1.14.0"
        const val core = "com.squareup.moshi:moshi:$version"
        const val codegen = "com.squareup.moshi:moshi-kotlin-codegen:$version"
        const val kotlin = "com.squareup.moshi:moshi-kotlin:$version"
    }

    // https://github.com/square/retrofit
    // https://square.github.io/okhttp/
    object Retrofit {
        private const val retrofit_version = "2.9.0"
        private const val interceptor_version = "4.9.1"

        const val core = "com.squareup.retrofit2:retrofit:$retrofit_version"
        const val converter_moshi = "com.squareup.retrofit2:converter-moshi:$retrofit_version"
        const val logging_interceptor =
            "com.squareup.okhttp3:logging-interceptor:$interceptor_version"
    }

    object Ktor {
        private const val version = "2.2.4"

        const val core = "io.ktor:ktor-client-core:$version"
        const val engine = "io.ktor:ktor-client-okhttp:$version"
        const val logging = "io.ktor:ktor-client-logging:$version"
        const val content_negotiation = "io.ktor:ktor-client-content-negotiation:$version"
        const val json_serialization = "io.ktor:ktor-serialization-kotlinx-json:$version"
    }

    // https://google.github.io/accompanist
    object Accompanist {
        private const val version = "0.30.1"
        const val swipeRefresh = "com.google.accompanist:accompanist-swiperefresh:$version"
        const val placeholder = "com.google.accompanist:accompanist-placeholder-material:$version"
        const val navAnimation = "com.google.accompanist:accompanist-navigation-animation:$version"
        const val permission = "com.google.accompanist:accompanist-permissions:$version"
    }

    object JUnit {
        private const val jUVersion = "4.13.2"
        private const val version = "1.1.5"

        const val core = "junit:junit:$jUVersion"
        const val androidx = "androidx.test.ext:junit:$version"

        // Added for Android 12 workaround (exported = true) TODO: Remove as soon as possible
        const val ktx = "androidx.test.ext:junit-ktx:$version"
    }

    @JvmStatic
    fun isNonStable(version: String): Boolean {
        val stableKeyword =
            listOf("RELEASE", "FINAL", "GA").any { version.uppercase(Locale.ROOT).contains(it) }
        val regex = "^[0-9,.v-]+(-r)?$".toRegex()
        val isStable = stableKeyword || regex.matches(version)
        return isStable.not()
    }
}

