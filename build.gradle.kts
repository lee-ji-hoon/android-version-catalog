buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath(libs.android.gradle)
        classpath(libs.dagger)
        classpath(libs.kotlin.plugin)
    }
}

