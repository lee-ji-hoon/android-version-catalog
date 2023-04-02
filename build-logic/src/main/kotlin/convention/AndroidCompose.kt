package convention

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureAndroidCompose(
    commonExtension: CommonExtension<*, *, *, *>
) {
    val libs = findVersionCatalog()

    commonExtension.apply {
        buildFeatures.compose = true

        composeOptions {
            kotlinCompilerExtensionVersion = libs.findVersion("compose.compiler").get().toString()
        }
    }

    dependencies {
        implementation(libs.findBundle("compose"))
    }
}