import io.gitlab.arturbosch.detekt.Detekt
import java.io.FileInputStream
import java.util.*
import org.jlleitschuh.gradle.ktlint.KtlintExtension

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("io.gitlab.arturbosch.detekt")
    id("org.jlleitschuh.gradle.ktlint")
}

android {
    compileSdk = 30

    defaultConfig {
        applicationId = "com.example.task5"
        minSdk = 27
        targetSdk = 30
        versionCode = 1
        versionName = "1.0"

        // add api key
        buildConfigField("String", "CAT_API_KEY", getApiKey())
    }
    buildTypes {
        debug {
            applicationIdSuffix = ".debug"
            isShrinkResources = false
            isMinifyEnabled = false
            proguardFiles("proguard-android-optimize.txt", "proguard-rules.pro")
        }
        release {
            isShrinkResources = true
            isMinifyEnabled = true
            proguardFiles("proguard-android-optimize.txt", "proguard-rules.pro")
        }
    }
    buildFeatures {
        viewBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
    detekt {
        buildUponDefaultConfig = true
        allRules = false
        config = files("${rootProject.projectDir}/config/detekt.yml")
        baseline = file("${rootProject.projectDir}/config/baseline.xml")

        reports {
            html {
                enabled = true
            }
        }
    }
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.3.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.1")
    implementation("androidx.core:core-ktx:1.6.0")
    implementation("androidx.fragment:fragment-ktx:1.3.6")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.3.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.1")
    implementation("androidx.paging:paging-runtime-ktx:3.0.1")
    implementation("com.google.android.material:material:1.4.0")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-moshi:2.9.0")
    implementation("com.squareup.moshi:moshi:1.12.0")
    kapt("com.squareup.moshi:moshi-kotlin-codegen:1.12.0")
    implementation("io.coil-kt:coil:1.3.2")
    implementation("io.coil-kt:coil-gif:1.3.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2")
}

tasks.withType<Detekt>().configureEach {
    jvmTarget = "1.8"
}

configure<KtlintExtension> {
    debug.set(true)
}

fun getApiKey(apiKey: String = "CAT_API_KEY"): String {
    val apiKeyProperties = Properties().apply {
        load(FileInputStream(rootProject.file("apikey.properties")))
    }
    return apiKeyProperties[apiKey] as String
}
