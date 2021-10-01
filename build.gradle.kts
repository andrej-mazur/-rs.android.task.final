// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    val gradleVersion = "7.0.2"
    val kotlinVersion = "1.5.31"
    val navigationVersion = "2.3.3"
    val detektVersion = "1.18.0"
    val ktlintVersion = "10.2.0"

    repositories {
        google()
        mavenCentral()
        maven("https://plugins.gradle.org/m2/")
    }

    dependencies {
        classpath("com.android.tools.build:gradle:$gradleVersion")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$navigationVersion")
        classpath("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:$detektVersion")
        classpath("org.jlleitschuh.gradle:ktlint-gradle:$ktlintVersion")

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
