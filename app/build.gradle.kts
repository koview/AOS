import java.io.FileInputStream
import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    namespace = "com.example.koview"
    compileSdk = 34

    Properties().apply {
        load(FileInputStream("$rootDir/local.properties"))
    }

    defaultConfig {
        applicationId = "com.example.koview"
        minSdk = 24
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        dataBinding = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // hilt
    val hiltVersion = "2.48"
    implementation("com.google.dagger:hilt-android:${hiltVersion}")
    kapt("com.google.dagger:hilt-android-compiler:${hiltVersion}")

    // retrofit
    val retrofitVersion = "2.9.0"
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-gson:$retrofitVersion")

    // okHttp
    val okHttpVersion = "5.0.0-alpha.2"
    implementation("com.squareup.okhttp3:okhttp:$okHttpVersion")
    implementation("com.squareup.okhttp3:logging-interceptor:$okHttpVersion")
    implementation("com.squareup.okhttp3:okhttp-urlconnection:$okHttpVersion")

    // glide
    implementation("com.github.bumptech.glide:glide:4.16.0")
    implementation("com.github.bumptech.glide:compose:1.0.0-beta01")

    // navigation
    implementation("androidx.navigation:navigation-fragment-ktx:2.5.1")
    implementation("androidx.navigation:navigation-ui-ktx:2.5.1")

    // CircleImageView
    implementation("de.hdodenhof:circleimageview:3.1.0")

    // LiveData
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.5.1")

    // ViewPager
    implementation ("androidx.viewpager2:viewpager2:1.0.0")

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}