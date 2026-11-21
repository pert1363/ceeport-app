plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.ceeport.player"
    compileSdk = 36 // 🔴 ارتقا به 36

    defaultConfig {
        applicationId = "com.ceeport.player"
        minSdk = 26
        targetSdk = 36 // 🔴 ارتقا به 36
        versionCode = 1
        versionName = "1.0"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }
}

repositories {
    maven {
        url = uri("https://maven.mozilla.org/maven2/")
    }
}

dependencies {
    // موتور GeckoView
    implementation("org.mozilla.geckoview:geckoview:121.0.20240226151529")
    
    // 🔴 کتابخانه‌های Media3 که باعث خطا شده بودند (نسخه پایدار 1.4.1)
    implementation("androidx.media3:media3-exoplayer:1.4.1")
    implementation("androidx.media3:media3-datasource:1.4.1")
    implementation("androidx.media3:media3-extractor:1.4.1")
    implementation("androidx.media3:media3-decoder:1.4.1")
}
