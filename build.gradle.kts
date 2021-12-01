// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    val gradleVersion = "7.0.3"
    val hiltVersion = "2.38.1"
    val detektVersion = "1.18.0"
    val kotlinVersion = "1.5.31"

    repositories {
        google()
        jcenter() /* TODO remove*/
    }

    dependencies {
        classpath("com.android.tools.build:gradle:$gradleVersion")
        classpath("com.google.dagger:hilt-android-gradle-plugin:$hiltVersion")
        classpath("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:$detektVersion")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}