plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("com.google.gms.google-services")
}

val properties = org.jetbrains.kotlin.konan.properties.Properties()
properties.load(project.rootProject.file("local.properties").inputStream())

android {
    namespace = "kr.timoky.accountbook"
    compileSdk = properties.getProperty("compileSdk").toInt()

    defaultConfig {
        applicationId = "kr.timoky.accountbook"
        minSdk = properties.getProperty("minSdk").toInt()
        targetSdk = properties.getProperty("targetSdk").toInt()
        versionCode = properties.getProperty("versionCode").toInt()
        versionName = properties.getProperty("versionName")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            isJniDebuggable = true
            applicationIdSuffix = ".debug"
            signingConfig = signingConfigs.getByName("debug")
            resValue("string", "app_name", "@string/app_name_debug")

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        release {
            isMinifyEnabled = true
            resValue("string", "app_name", "@string/app_name_release")

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        dataBinding = true
        viewBinding = true
    }
}

dependencies {
    implementation(project(":data"))
    implementation(project(":domain"))

    val googleFirebaseVersion = properties.getProperty("google_firebase_version")
    val hiltVersion = properties.getProperty("hilt_version")
    val lifecycleVersion = properties.getProperty("lifecycle_version")
    val roomVersion = properties.getProperty("room_version")
    val retrofitVersion = properties.getProperty("retrofit_version")
    val okhttpVersion = properties.getProperty("okhttp_version")
    val coroutineVersion = properties.getProperty("coroutine_version")
    val paging3Version = properties.getProperty("paging3_version")
    val navigationVersion = properties.getProperty("navigation_version")
    val lottieVersion = properties.getProperty("lottie_version")
    val shimmerVersion = properties.getProperty("shimmer_version")
    val coilVersion = properties.getProperty("coil_version")
    val swiperefreshVersion = properties.getProperty("swiperefresh_version")

    implementation("com.google.android.material:material:1.7.0")
    testImplementation("junit:junit:4.13.2")

    // Hilt
    implementation("com.google.dagger:hilt-android:$hiltVersion")
    kapt("com.google.dagger:hilt-android-compiler:$hiltVersion")

    // Retrofit + Okhttp
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-gson:$retrofitVersion")
    implementation("com.squareup.okhttp3:okhttp:$okhttpVersion")
    implementation("com.squareup.okhttp3:logging-interceptor:$okhttpVersion")

    // Gson
    implementation("com.google.code.gson:gson:2.10")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // androidX <
    implementation("androidx.activity:activity-ktx:1.6.1")
    implementation("androidx.fragment:fragment-ktx:1.5.5")

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.5.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    androidTestImplementation("androidx.test.ext:junit:1.1.4")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.0")

    // Lifecycle
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.5.1")
    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")
    // LiveData
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion")
    // Lifecycles only (without ViewModel or LiveData)
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion")
    // optional - helpers for implementing LifecycleOwner in a Service
    implementation("androidx.lifecycle:lifecycle-service:$lifecycleVersion")
    // SwipeRefreshLayout
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:$swiperefreshVersion")
    // >

    // room
    implementation("androidx.room:room-runtime:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")
    kapt("androidx.room:room-compiler:$roomVersion")

    // Coroutine
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutineVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutineVersion")

    // Paging 3.0
    implementation("androidx.paging:paging-runtime-ktx:$paging3Version")
    testImplementation("androidx.paging:paging-common:$paging3Version")

    // Navigation
    implementation("androidx.navigation:navigation-fragment-ktx:$navigationVersion")
    implementation("androidx.navigation:navigation-ui-ktx:$navigationVersion")

    // Lottie
    implementation("com.airbnb.android:lottie-compose:$lottieVersion")

    // Shimmer
    implementation("com.facebook.shimmer:shimmer:$shimmerVersion")

    // Coil
    implementation("io.coil-kt:coil:$coilVersion")

    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:$googleFirebaseVersion"))
}