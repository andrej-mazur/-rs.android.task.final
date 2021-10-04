import io.gitlab.arturbosch.detekt.Detekt
import org.jlleitschuh.gradle.ktlint.KtlintExtension
import java.io.FileInputStream
import java.util.Properties

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("androidx.navigation.safeargs.kotlin")
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
    implementation(Libraries.appcompat)
    implementation(Libraries.constraintlayout)
    implementation(Libraries.coreKtx)
    implementation(Libraries.fragmentKtx)
    implementation(Libraries.lifecycleLivedataKtx)
    implementation(Libraries.lifecycleViewmodelKtx)
    implementation(Libraries.navigationFragmentKtx)
    implementation(Libraries.navigationUiKtx)
    implementation(Libraries.pagingRuntimeKtx)
    implementation(Libraries.material)
    implementation(Libraries.okhttp)
    implementation(Libraries.retrofit)
    implementation(Libraries.retrofitConverterMoshi)
    implementation(Libraries.moshi)
    kapt(Libraries.moshiCodegen)
    implementation(Libraries.coil)
    implementation(Libraries.coilGif)
    implementation(Libraries.coroutinesAndroid)
    implementation(Libraries.coroutinesCore)
}

tasks.withType<Detekt>().configureEach {
    jvmTarget = JavaVersion.VERSION_1_8.toString()
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
