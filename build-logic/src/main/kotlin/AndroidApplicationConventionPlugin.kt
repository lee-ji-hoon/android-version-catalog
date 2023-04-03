import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.variant.ApplicationAndroidComponentsExtension
import convention.configureKotlinAndroid
import convention.findVersionCatalog
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure

class AndroidApplicationConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            val libs = findVersionCatalog()

            with(pluginManager) {
                apply(libs.findPlugin("android.application"))
                apply(libs.findPlugin("kotlin.android"))
                apply(libs.findPlugin("dagger.hilt.android"))
            }

            extensions.configure<ApplicationExtension> {
                configureKotlinAndroid(this)
                defaultConfig {
                    targetSdk = libs.findVersion("target.sdk").get().toString().toInt()
                }
                buildFeatures {
                    dataBinding = true
                }
            }


            /*
            TODO [koni] 해당 속성 어떻게 사용중인지 확인 필요
            extensions.configure<ApplicationAndroidComponentsExtension> {
                configBuildConfigField(this)
            }
            */
        }
    }
}