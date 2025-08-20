import java.util.Properties

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.hilt)
    alias(libs.plugins.org.jetbrains.kotlin.kapt)
    alias(libs.plugins.detekt)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.devsapiens.finanzapp"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.devsapiens.finanzapp"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        val config = rootProject.file("env.properties").inputStream().use {
            Properties().apply { load(it) }
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "BASE_URL", config.getProperty("API_PROD"))
        }
        debug {
            applicationIdSuffix = ".debug"
            buildConfigField("String", "BASE_URL", config.getProperty("API_DEV"))
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.hilt.android)
    implementation(libs.androidx.runtime.livedata)
    implementation(libs.androidx.navigation.navigation)
    implementation(libs.androidx.navigation.navigation.hilt)
    implementation(libs.airbnb.android.lottie)
    implementation(libs.retrofit)
    implementation(libs.retrofit.jackson)
    implementation(libs.retrofit.coroutines)
    implementation(libs.jackson.core)
    implementation(libs.jackson.annotation)
    implementation(libs.jackson.databind)
    implementation(libs.jackson.mapper)
    implementation(libs.androidx.datastore.core.android)
    implementation(libs.androidx.datastore)
    implementation(libs.ok.http.interceptor)
    implementation(libs.room.runtime)
    implementation(libs.google.accompanist)
    implementation(libs.android.foundation)
    kapt(libs.room.compiler)
    implementation(libs.room.ktx)
    kapt(libs.hilt.compiler)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}

kapt {
    correctErrorTypes = true
}