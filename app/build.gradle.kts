plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    kotlin("kapt")
    id("com.google.dagger.hilt.android") version "2.54"
}

android {
    namespace = "com.example.kpzparcel"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.kpzparcel"
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    // Coil for image loading
    implementation("io.coil-kt:coil-compose:2.7.0")

    // AndroidX libraries
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.material.icons.extended)
    implementation(libs.material)
    implementation(libs.androidx.espresso.core)
    implementation(libs.transport.runtime)
    implementation(libs.androidx.runtime.livedata)

    // JUnit for unit testing
    testImplementation(libs.junit)

    // Android test libraries
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Navigation Component for Compose
    implementation("androidx.navigation:navigation-compose:2.8.5")

    // Room components for database
    implementation("androidx.room:room-runtime:2.7.0-alpha12")  // Updated Room version
    kapt("androidx.room:room-compiler:2.7.0-alpha12")
    implementation("androidx.room:room-ktx:2.7.0-alpha12")
    androidTestImplementation("androidx.room:room-testing:2.7.0-alpha12")

    // Lifecycle components (ViewModel, LiveData)
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation("androidx.lifecycle:lifecycle-common-java8:2.8.7")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.7")

    // Kotlin standard library and coroutines
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:2.1.0")
    api("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.1")
    api("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.10.1")

    // Hilt for dependency injection
    implementation("com.google.dagger:hilt-android:2.54")  // Updated Hilt version
    kapt("com.google.dagger:hilt-compiler:2.54")
    implementation("com.google.dagger:hilt-android-gradle-plugin:2.54")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")
}
