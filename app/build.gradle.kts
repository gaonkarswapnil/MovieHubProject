import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)

//    id("com.android.application")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    kotlin("kapt")
}

android {
    namespace = "com.example.movie"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.movie"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"


        val properties = Properties().apply {
            load(project.rootProject.file("secret.properties").inputStream())
        }
        buildConfigField("String", "API_KEY", properties.getProperty("API_KEY"))

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.firebase.auth)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

//    SplashScreen
    implementation("androidx.core:core-splashscreen:1.0.1")

    // Kotlin Coroutines Core Library
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")

    // Kotlin Coroutines Android Library for Main Thread operations
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")


//    GSON
    implementation("com.google.code.gson:gson:2.11.0")

//    Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")

    implementation("com.squareup.okhttp3:okhttp:4.12.0")

    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")


//    Glide
    implementation("com.github.bumptech.glide:glide:4.15.0")



    implementation("com.squareup.retrofit2:adapter-rxjava3:2.9.0")


//    RxKotlin
    implementation("io.reactivex.rxjava3:rxkotlin:3.0.0")

    // RxJava
    implementation("io.reactivex.rxjava3:rxjava:3.1.0")

    // RxAndroid
    implementation("io.reactivex.rxjava3:rxandroid:3.0.0")

    implementation("androidx.activity:activity-ktx:1.7.1")

//    Firebase Auth
    implementation("com.firebaseui:firebase-ui-auth:7.2.0")

    implementation("com.google.firebase:firebase-auth-ktx:22.0.0")


    // Import the BoM for the Firebase platform
    implementation(platform("com.google.firebase:firebase-bom:33.7.0"))

    // Add the dependencies for the Crashlytics and Analytics libraries
    // When using the BoM, you don't specify versions in Firebase library dependencies
    implementation("com.google.firebase:firebase-crashlytics")
    implementation("com.google.firebase:firebase-analytics")


//    Room Dependency
    val room_version = "2.6.1"

    implementation("androidx.room:room-runtime:$room_version")
    kapt("androidx.room:room-compiler:$room_version")
    implementation("androidx.room:room-ktx:$room_version")


    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.9.0")

}