plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
    id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp")
}

android {
    namespace = "my.mvp"
    compileSdk = 35

    defaultConfig {
        applicationId = "my.mvp"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        buildConfig = true
        compose = true
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.runtime.android)
    implementation(libs.androidx.storage)
    implementation(libs.androidx.foundation.android)
    implementation(libs.androidx.material3.android)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.coil.compose)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.squareup.picasso)
    implementation(libs.okhttp)
    implementation(libs.glide)
    implementation(libs.androidx.recyclerview)
    implementation(libs.androidx.viewbinding)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    implementation(libs.logging.interceptor)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.dagger.hilt.android)
    implementation(libs.androidx.hilt.navigation.compose.v100)
    implementation(libs.hilt.android.v248)
    implementation(libs.androidx.hilt.navigation.compose)

    implementation(libs.gson)

    implementation(libs.androidx.navigation.compose.v276)
    implementation(libs.androidx.constraintlayout.compose)
    implementation(libs.hilt.android.v251)
    ksp(libs.hilt.compiler.v251)

    implementation(libs.material.v1110)
}

secrets {
    propertiesFileName = "secrets.properties"
    defaultPropertiesFileName = "secrets.defaults.properties"
    ignoreList.add("keyToIgnore")
    ignoreList.add("sdk.*")
}