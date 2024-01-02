plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.myapplication"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.myapplication"
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
        viewBinding = true
    }
}


dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.9")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.9")
    implementation("com.squareup.okhttp3:okhttp:4.9.0")
    implementation("org.jsoup:jsoup:1.13.1")
    implementation("androidx.viewpager2:viewpager2:1.0.0")
    implementation("com.github.Dimezis:BlurView:version-2.0.3")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation ("com.google.android.material:material:1.5.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.6")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.6")
    implementation("androidx.exifinterface:exifinterface:1.3.7")
    implementation ("com.google.code.gson:gson:2.8.8")
    implementation ("com.squareup.picasso:picasso:2.71828")
    implementation ("com.github.bumptech.glide:glide:4.16.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.12.0")

}