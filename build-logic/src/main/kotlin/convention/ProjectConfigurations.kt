package convention

import org.gradle.api.JavaVersion

object ProjectConfigurations {
    const val compileSdk = 33
    const val targetSdk = 33
    const val minSdk = 23
    val javaVersion = JavaVersion.VERSION_11
}