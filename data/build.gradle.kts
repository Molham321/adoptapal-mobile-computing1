plugins {
    id(Libs.Plugins.android_library)
    id(Libs.Plugins.kotlin_android)
    id(Libs.Plugins.kotlin_kapt)
}


android {
    namespace = "de.fhe.adoptapal.data"
    compileSdk = Config.compile_sdk_version

    defaultConfig {
        minSdk = Config.min_sdk_version
        targetSdk = Config.target_sdk_version

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        javaCompileOptions {
            annotationProcessorOptions {
                arguments["room.schemaLocation"] = "$projectDir/schemas"
            }
        }
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

    kapt(Libs.Room.compiler)
    implementation(Libs.Room.runtime)
    implementation(Libs.Room.ktx)
    androidTestImplementation(Libs.Room.testing)

    testImplementation(Libs.JUnit.core)
    androidTestImplementation(Libs.JUnit.ktx)
    androidTestImplementation(Libs.AndroidX.Espresso.core)
}
