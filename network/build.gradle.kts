plugins {
    id(Libs.Plugins.android_library)
    id(Libs.Plugins.kotlin_android)
}

android {
    namespace = "de.fhe.adoptapal.network"
    compileSdk = Config.compile_sdk_version

    defaultConfig {
        minSdk = Config.min_sdk_version
        targetSdk = Config.target_sdk_version

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = Config.jvm_target
    }
}

dependencies {

    implementation(project(mapOf("path" to ":domain")))
    implementation(project(mapOf("path" to ":android-core")))
    implementation(Libs.AndroidX.coreKtx)
    implementation(Libs.Coroutines.android)

    implementation(Libs.Moshi.core)
    implementation(Libs.Moshi.codegen)
    implementation(Libs.Moshi.kotlin)

    implementation(Libs.Retrofit.core)
    implementation(Libs.Retrofit.converter_moshi)
    implementation(Libs.Retrofit.logging_interceptor)

    implementation(Libs.Ktor.core)
    implementation(Libs.Ktor.engine)
    implementation(Libs.Ktor.logging)
    implementation(Libs.Ktor.content_negotiation)
    implementation(Libs.Ktor.json_serialization)

    testImplementation(Libs.JUnit.core)
    androidTestImplementation(Libs.JUnit.ktx)
    androidTestImplementation(Libs.AndroidX.Espresso.core)
}