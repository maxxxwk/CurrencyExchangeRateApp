package com.example.buildsrc

object Apps {
    const val compileSdk = 31
    const val minSdk = 21
    const val targetSdk = 32
    const val versionCode = 1
    const val versionName = "1.0.0"
}

object Versions {
    const val kotlin = "1.6.10"
    const val gradle = "7.2"

    const val hilt = "2.38.1"
    const val hiltNavigationCompose = "1.0.0"

    const val compose = "1.1.1"
    const val activityCompose = "1.4.0"

    const val lifecycle = "2.4.1"

    const val navigation = "2.4.2"

    const val dagger = "2.41"

    const val kotlinxSerialization = "1.3.2"

    const val retrofit = "2.9.0"
    const val retrofit_converter = "0.8.0"

    const val okhttp = "4.9.3"

    const val room = "2.4.2"

    const val junit = "4.13.2"

    const val mockk = "1.12.3"

    const val coroutinesTest = "1.6.1"

}

object Libs {
    const val composeUI = "androidx.compose.ui:ui:${Versions.compose}"
    const val composeMaterial = "androidx.compose.material:material:${Versions.compose}"
    const val composePreview = "androidx.compose.ui:ui-tooling-preview:${Versions.compose}"

    const val activityCompose = "androidx.activity:activity-compose:${Versions.activityCompose}"

    const val viewModelCompose =
        "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.lifecycle}"
    const val runtimeKTX = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}"

    const val navigation = "androidx.navigation:navigation-compose:${Versions.navigation}"
    const val dagger = "com.google.dagger:dagger:${Versions.dagger}"

    const val hilt = "com.google.dagger:hilt-android:${Versions.hilt}"
    const val hiltNavigationCompose =
        "androidx.hilt:hilt-navigation-compose:${Versions.hiltNavigationCompose}"

    const val kotlinxSerialization =
        "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.kotlinxSerialization}"

    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val retrofitConverter =
        "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:${Versions.retrofit_converter}"

    const val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okhttp}"
    const val okhttpLogging = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp}"

    const val room = "androidx.room:room-runtime:${Versions.room}"
    const val roomKTX = "androidx.room:room-ktx:${Versions.room}"
}

object TestLibs {
    const val junit = "junit:junit:${Versions.junit}"
    const val mockk = "io.mockk:mockk:${Versions.mockk}"
    const val coroutinesTest =
        "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutinesTest}"
}

object DebugLibs {
    const val composeTooling = "androidx.compose.ui:ui-tooling:${Versions.compose}"
}

object KaptCompilers {
    const val dagger = "com.google.dagger:dagger-compiler:${Versions.dagger}"
    const val hilt = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"
    const val room = "androidx.room:room-compiler:${Versions.room}"
}