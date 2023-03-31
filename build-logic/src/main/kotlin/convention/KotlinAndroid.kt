package convention

import com.android.build.gradle.BaseExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

internal fun Project.configureAndroid() {

    extensions.configure<BaseExtension> {
        compileSdkVersion(ProjectConfigurations.compileSdk)

        defaultConfig {
            minSdk = ProjectConfigurations.minSdk
            targetSdk = ProjectConfigurations.targetSdk
            testInstrumentationRunner =
                "android.support.test.runner.AndroidJunitRunner" // TODO [koni] 얘 뭔지 알아야 함
            vectorDrawables.useSupportLibrary // TODO [koni] 얘 뭔지 알아야 함
        }

        dataBinding.enable = true

        compileOptions {
            sourceCompatibility = ProjectConfigurations.javaVersion
            targetCompatibility = ProjectConfigurations.javaVersion
        }
    }
}
