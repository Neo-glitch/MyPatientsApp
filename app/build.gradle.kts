plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("kotlinx-serialization")
    id("com.google.dagger.hilt.android")
    kotlin("kapt")
}

android {
    namespace = "com.neo.mypatients"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.neo.mypatients"
        minSdk = 26
        targetSdk = 36
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
        compose = true
        buildConfig = true
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

    // Compose & Navigation
    implementation(libs.navigation.compose)
    implementation(libs.lifecycle.viewmodel.compose)
    implementation(libs.lifecycle.runtime.compose)
    implementation(libs.compose.material.icons)
    implementation(libs.compose.ui.util)

    // Paging
    implementation(libs.paging.compose)
    implementation(libs.paging.runtime)
    testImplementation(libs.paging.testing)

    // Coil
    implementation(libs.coil.compose)

    // Networking
    implementation(libs.serialization.converter)
    implementation(libs.retrofit)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging)
    implementation(libs.serialization.json)

    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)
    implementation(libs.hilt.work)
    kapt(libs.hilt.work.compiler)
    androidTestImplementation(libs.hilt.testing)
    kaptAndroidTest(libs.hilt.compiler)

    // Room
    implementation(libs.room.runtime)
    kapt(libs.room.compiler)
    implementation(libs.room.ktx)
    implementation(libs.room.paging)

    // Splash
    implementation(libs.splashscreen)

    // WorkManager
    implementation(libs.work.runtime.ktx)

    // Testing
    implementation(libs.truth)
    androidTestImplementation(libs.truth)
    testImplementation(libs.robolectric)
    testImplementation(libs.coroutines.test)
    androidTestImplementation(libs.coroutines.test)
    testImplementation(libs.mockwebserver)
    androidTestImplementation(libs.mockwebserver)
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.arch.core.testing)
    androidTestImplementation(libs.arch.core.testing)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

//    // navigation
//    val navVersion = "2.7.2"
//    implementation("androidx.navigation:navigation-compose:$navVersion")
//    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")
//    implementation ("androidx.compose.material:material-icons-extended:1.4.1")
//    implementation ("androidx.compose.ui:ui-util")
//    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")
//    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.2")
//
//    //Paging3
//    val paging_version = "3.2.1"
//    implementation("androidx.paging:paging-compose:$paging_version")
//    implementation("androidx.paging:paging-runtime-ktx:$paging_version")
//    // paging3 test
//    testImplementation("androidx.paging:paging-testing:$paging_version")
//
//    //Coil
//    implementation("io.coil-kt:coil-compose:2.1.0")
//
//    // Network
//    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:1.0.0")
//    implementation("com.squareup.retrofit2:retrofit:2.9.0")
//    implementation("com.squareup.okhttp3:okhttp:4.9.0")
//    implementation("com.squareup.okhttp3:logging-interceptor:4.9.0")
//    implementation ("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
//
//    // dagger Hilt
//    implementation("com.google.dagger:hilt-android:2.50")
//    kapt("com.google.dagger:hilt-android-compiler:2.50")
//    implementation ("androidx.hilt:hilt-navigation-compose:1.0.0")
//    // For instrumented tests.
//    androidTestImplementation("com.google.dagger:hilt-android-testing:2.50")
//    // ...with Kotlin.
//    kaptAndroidTest("com.google.dagger:hilt-android-compiler:2.50")
//
//    val roomVersion = "2.6.1"
//    implementation("androidx.room:room-runtime:$roomVersion")
//    kapt("androidx.room:room-compiler:$roomVersion")
//    implementation("androidx.room:room-ktx:$roomVersion")
//    implementation("androidx.room:room-paging:$roomVersion")
//
//    //splash
//    implementation ("androidx.core:core-splashscreen:1.0.1")
//
//    //for test assertion
//    implementation("com.google.truth:truth:1.1.2")
//    androidTestImplementation("com.google.truth:truth:1.1.2")
//
//    // robolectric
//    testImplementation("org.robolectric:robolectric:4.14")
//
//    // coroutine test
//    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.9.0-RC.2")
//    androidTestImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.9.0-RC.2")
//
//    // for mocking server for test
//    testImplementation("com.squareup.okhttp3:mockwebserver:4.12.0")
//    androidTestImplementation("com.squareup.okhttp3:mockwebserver:4.12.0")
//
//    // mockito
//    testImplementation("org.mockito:mockito-core:5.12.0")
//    testImplementation("org.mockito.kotlin:mockito-kotlin:5.4.0")
//
//    // helper to test android arch components e.g viewmodel, livedata and also helps exec arch components synchronously
//    androidTestImplementation("androidx.arch.core:core-testing:2.2.0")
//    testImplementation("androidx.arch.core:core-testing:2.2.0")
}