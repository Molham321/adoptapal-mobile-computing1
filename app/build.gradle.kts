import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import com.android.build.api.variant.BuildConfigField

plugins {
    id(Libs.Plugins.android_app)
    id(Libs.Plugins.kotlin_android)
}

// get API key
val latLongApiKey: String = gradleLocalProperties(rootDir).getProperty("LAT_LONG_API")
val mapsApiKey: String = gradleLocalProperties(rootDir).getProperty("MAPS_API_KEY")


// Components to get data in android application
androidComponents {
    onVariants {
        it.buildConfigFields.put(
            "LAT_LONG_API_KEY", BuildConfigField(
                "String", "\"$latLongApiKey\"", "API Key for LatLong conversion from application.properties"
            )
        )
        it.buildConfigFields.put(
            "MAPS_API_KEY", BuildConfigField(
                "String", "\"$mapsApiKey\"", "Google Maps Api Key from application.properties"
            )
        )
    }
}

android {
    namespace = "de.fhe.adoptapal"
    compileSdk = Config.compile_sdk_version
    buildToolsVersion = Config.build_tools_version

    defaultConfig {
        applicationId = Config.application_id
        minSdk = Config.min_sdk_version
        targetSdk = Config.target_sdk_version
        versionCode = Config.version_code
        versionName = Config.version_name

        // set api key to AndroidManifest from application.properties
        manifestPlaceholders["MAPS_API_KEY"] = mapsApiKey

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
//
//        // Specify Network Implementation
//        // Possible Values: DEFAULT (Mock), RETROFIT, KTOR
//        buildConfigField(
//            type = "de.fhe.ai.pmc.acat.network.core.NetworkImplType",
//            name = "NET_IMPL_TYPE",
//            value = "de.fhe.ai.pmc.acat.network.core.DEFAULT.INSTANCE"
//        )
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
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Libs.AndroidX.Compose.compiler_version
    }
    packaging {
        resources.excludes.add("/META-INF/{AL2.0,LGPL2.1}")
    }
}

dependencies {

    implementation(project(mapOf("path" to ":domain")))
    implementation(project(mapOf("path" to ":android-core")))
    implementation(project(mapOf("path" to ":data")))
//    implementation(project(mapOf("path" to ":network")))

    implementation(Libs.Coroutines.core)
    implementation(Libs.Coroutines.android)

    implementation(Libs.AndroidX.coreKtx)
    implementation(Libs.AndroidX.Activity.activityCompose)
    implementation(Libs.AndroidX.Compose.ui)
    implementation(Libs.AndroidX.Compose.material)
    implementation(Libs.AndroidX.Compose.toolingPreview)
    implementation(Libs.AndroidX.Navigation.compose)
    implementation(Libs.AndroidX.Lifecycle.runtime)
    implementation(Libs.AndroidX.Lifecycle.viewmodel)
    implementation(Libs.AndroidX.Lifecycle.viewModelCompose)

    implementation(Libs.Koin.core)
    implementation(Libs.Koin.android)
    implementation(Libs.Koin.compose)
    implementation(Libs.Koin.navigation)

    implementation(Libs.AndroidX.MaterialDesign.core)

    implementation( Libs.AndroidX.Maps.core )
    implementation( Libs.AndroidX.Maps.play_services )
    implementation( Libs.AndroidX.Maps.location )

    implementation( Libs.Accompanist.navAnimation )
    implementation( Libs.Accompanist.placeholder )

    testImplementation(Libs.JUnit.core)
    androidTestImplementation(Libs.AndroidX.Compose.uiTest)
    androidTestImplementation(Libs.AndroidX.Espresso.core)
    androidTestImplementation(Libs.JUnit.ktx)
    androidTestImplementation(Libs.Koin.test)

    debugImplementation(Libs.AndroidX.Compose.tooling)
}