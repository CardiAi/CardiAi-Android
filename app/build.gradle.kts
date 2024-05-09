import java.util.Properties

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.hilt)
    kotlin("kapt")
    alias(libs.plugins.ksp)
//    alias(libs.plugins.googleServices)
//    alias(libs.plugins.firebaseCrashlytics)
}

val localPropertiesFile = rootProject.file("local.properties")
val localProperties = Properties().apply {
    load(localPropertiesFile.inputStream())
}

val userEmail: String = localProperties.getProperty("userEmail")
val userPass: String = localProperties.getProperty("userPass")

android {
    namespace = "com.codlin.cardiai"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.codlin.cardiai"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
            buildConfigField("String", "userEmail", userEmail)
            buildConfigField("String", "userPass", userPass)
            isDebuggable = true
        }

        release {
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
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
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
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
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation(libs.lifecycle.runtime.compose)
    implementation(libs.extended.icons)
    implementation(libs.splashscreen)
    implementation(kotlin("reflect"))
}

// Hilt Dependency Injection
dependencies {
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    implementation(libs.hilt.navigation)
}

// Retrofit
dependencies {
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)
    implementation(libs.logging.interceptor)
}

// Compose Destinations
dependencies {
    implementation(libs.destinations.core)
    ksp(libs.destinations.ksp)
}

// Datastore
dependencies {
    implementation(libs.datastore.preferences)
}

// Paging
dependencies {
    implementation(libs.paging.compose)
    implementation(libs.paging.runtime)
}

// Logging
dependencies {
    implementation(libs.timber)
}

// Firebase
//dependencies {
//    implementation(platform(libs.firebase.bom))
//    implementation(libs.firebase.crashlytics)
//    implementation(libs.firebase.analytics)
//}