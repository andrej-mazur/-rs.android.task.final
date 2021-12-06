// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    val gradleVersion = "7.0.3"
    val hiltVersion = "2.38.1"
    val detektVersion = "1.18.0"
    val kotlinVersion = "1.5.31"
    val navigationVersion = "2.4.0-alpha02"

    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:$gradleVersion")
        classpath("com.google.dagger:hilt-android-gradle-plugin:$hiltVersion")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$navigationVersion")
        classpath("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:$detektVersion")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}