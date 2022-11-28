plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

val properties = org.jetbrains.kotlin.konan.properties.Properties()
properties.load(project.rootProject.file("local.properties").inputStream())

android {
    namespace = "kr.timoky.domain"
    compileSdk = properties.getProperty("compileSdk").toInt()

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    val kotlinVersion = properties.getProperty("kotlin_version")
    val coroutineVersion = properties.getProperty("coroutine_version")

    // Kotlin
    implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")

    // JavaX Inject
    implementation("javax.inject:javax.inject:1")

    // Coroutine
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutineVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutineVersion")
}