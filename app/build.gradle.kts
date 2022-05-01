import com.example.buildsrc.*
import java.util.Properties
import java.io.FileInputStream

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("org.jetbrains.kotlin.plugin.serialization") version "1.6.10"
    id("dagger.hilt.android.plugin")
}

val localProperties = Properties().apply {
    load(FileInputStream(rootProject.file("local.properties")))
}

android {

    compileSdk = Apps.compileSdk

    defaultConfig {
        applicationId = "com.example.currencyexchangerateapp"
        minSdk = Apps.minSdk
        targetSdk = Apps.targetSdk
        versionCode = Apps.versionCode
        versionName = Apps.versionName
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        vectorDrawables {
            useSupportLibrary = true
        }

        buildConfigField("String", "API_KEY", localProperties.getProperty("API_KEY"))
        buildConfigField("String", "API_URL", "\"https://www.amdoren.com/\"")
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions.jvmTarget = JavaVersion.VERSION_11.toString()
    buildFeatures.compose = true
    composeOptions.kotlinCompilerExtensionVersion = Versions.compose

    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    //compose
    implementation(Libs.composeUI)
    implementation(Libs.composeMaterial)
    implementation(Libs.composePreview)

    implementation(Libs.activityCompose)

    //lifecycle
    implementation(Libs.viewModelCompose)
    implementation(Libs.runtimeKTX)

    //navigation
    implementation(Libs.navigation)

    //dagger 2
    implementation(Libs.dagger)
    kapt(KaptCompilers.dagger)

    //hilt
    implementation(Libs.hilt)
    kapt(KaptCompilers.hilt)
    implementation(Libs.hiltNavigationCompose)

    //kotlinx-serialization
    implementation(Libs.kotlinxSerialization)

    //retrofit 2
    implementation(Libs.retrofit)
    implementation(Libs.retrofitConverter)

    //okhttp
    implementation(Libs.okhttp)
    implementation(Libs.okhttpLogging)

    //room
    implementation(Libs.room)
    kapt(KaptCompilers.room)
    implementation(Libs.roomKTX)

    //test
    testImplementation(TestLibs.junit)
    testImplementation(TestLibs.mockk)
    testImplementation(TestLibs.coroutinesTest)

    //debug
    debugImplementation(DebugLibs.composeTooling)
}