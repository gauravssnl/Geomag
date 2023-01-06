@file:Suppress("UnstableApiUsage")

plugins {
    id("com.android.application")
    id("com.google.devtools.ksp")
    kotlin("android")
}

val verName = "0.2.0"
val verCode = 20

android {
    namespace = "com.sanmer.geomag"
    compileSdk = 33
    buildToolsVersion = "33.0.1"
    ndkVersion = "25.1.8937393"

    signingConfigs {
        create("release") {
            enableV2Signing = true
            enableV3Signing = true
        }
    }

    defaultConfig {
        applicationId = namespace
        minSdk = 26
        targetSdk = 33
        versionCode = verCode
        versionName = verName
        resourceConfigurations += arrayOf("en", "zh-rCN")
        multiDexEnabled = true

        ndk {
            abiFilters += arrayOf("armeabi-v7a", "arm64-v8a")
        }

        externalNativeBuild {
            cmake {
                arguments += "-DANDROID_STL=c++_static"
            }
        }
    }

    dependenciesInfo.includeInApk = false

    buildTypes {
        getByName("release") {
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = true
            isShrinkResources = true
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
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.0-alpha02"
    }

    externalNativeBuild {
        cmake {
            path = file("src/main/cpp/CMakeLists.txt")
            version = "3.22.1"
        }
    }

    sourceSets {
        getByName("main") {
            jniLibs.srcDir("libs")
        }
    }

    packagingOptions {
        resources {
            excludes += setOf(
                "META-INF/**",
                "okhttp3/**",
                "kotlin/**",
                "org/**",
                "**.properties",
                "**.bin",
                "**/*.proto"
            )
        }
    }

    setProperty("archivesBaseName", "geomag-$verName")
    splits {
        abi {
            reset()
            include("armeabi-v7a", "arm64-v8a")
            isEnable = true
            isUniversalApk = true
        }
    }

    ksp {
        arg("room.incremental", "true")
        arg("room.schemaLocation", "$projectDir/schemas")
    }

}


kotlin.sourceSets.all {
    languageSettings {
        optIn("androidx.compose.material3.ExperimentalMaterial3Api")
        optIn("androidx.compose.ui.ExperimentalComposeUiApi")
        optIn("androidx.compose.animation.ExperimentalAnimationApi")
        optIn("androidx.compose.foundation.ExperimentalFoundationApi")
        optIn("com.google.accompanist.permissions.ExperimentalPermissionsApi")
        optIn("kotlin.ExperimentalStdlibApi")
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.lifecycle:lifecycle-service:2.5.1")
    implementation("com.google.android.material:material:1.8.0-beta01")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")

    implementation("androidx.activity:activity-compose:1.6.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.5.1")
    implementation("androidx.navigation:navigation-compose:2.5.3")
    implementation("androidx.compose.material3:material3:1.1.0-alpha03")

    val vCompose = "1.4.0-alpha03"
    implementation("androidx.compose.ui:ui:${vCompose}")
    implementation("androidx.compose.ui:ui-tooling-preview:${vCompose}")
    debugImplementation("androidx.compose.ui:ui-tooling:${vCompose}")
    debugImplementation("androidx.compose.ui:ui-test-manifest:${vCompose}")

    val vAccompanist = "0.28.0"
    implementation("com.google.accompanist:accompanist-systemuicontroller:${vAccompanist}")
    implementation("com.google.accompanist:accompanist-permissions:${vAccompanist}")
    implementation("com.google.accompanist:accompanist-navigation-animation:${vAccompanist}")

    val vRoom = "2.4.3"
    implementation("androidx.room:room-runtime:${vRoom}")
    implementation("androidx.room:room-ktx:${vRoom}")
    ksp("androidx.room:room-compiler:${vRoom}")

    val vMoshi = "1.14.0"
    implementation("com.squareup.moshi:moshi:${vMoshi}")
    ksp("com.squareup.moshi:moshi-kotlin-codegen:${vMoshi}")

    implementation("com.jakewharton.timber:timber:5.0.1")
}