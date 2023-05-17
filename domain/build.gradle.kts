plugins {
    id(Libs.Plugins.java_library)
    id(Libs.Plugins.kotlin_jvm)
}

dependencies {
    implementation( Libs.Coroutines.core )
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}