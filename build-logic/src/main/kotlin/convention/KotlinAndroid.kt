package convention

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.provideDelegate
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

internal fun Project.configureKotlinAndroid(
    commonExtension: CommonExtension<*, *, *, *>,
) {

    validateVersions()

    commonExtension.apply {
        compileSdk = findVersionCatalog().findVersion("compile_sdk").get().toString().toInt()

        defaultConfig {
            minSdk = findVersionCatalog().findVersion("min_sdk").get().toString().toInt()
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_11
            targetCompatibility = JavaVersion.VERSION_11
        }

        kotlinOptions {

            /**
             * Warning을 에러로 처리할 지 여부 설정
             * TODO [koni] 해당 속성은 gradle.properteis에서 warning을 errror로 처리할지 말지 정하는 속성이다.
             * 그런데 회사 코드에서 warningsAsErrors에 대한 속성이 없는 모습이니 질문하자.
             */
            val warningsAsErrors: String? by project
            allWarningsAsErrors = warningsAsErrors.toBoolean()

            // 새로운 값을 추가할 때는 기존 설정값을 덮어쓰지 않도록 주의해야 합니다.
            freeCompilerArgs = freeCompilerArgs + listOf(
                "-opt-in=kotlin.RequiresOptIn",
                // Experimental coroutines APIs를 사용할 수 있게 해주는 설정
                "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
                "-opt-in=kotlinx.coroutines.FlowPreview",
                "-opt-in=kotlin.Experimental",
            )

            jvmTarget = JavaVersion.VERSION_11.toString()
        }

        dataBinding.enable = true
    }
}

fun CommonExtension<*, *, *, *>.kotlinOptions(block: KotlinJvmOptions.() -> Unit) {
    (this as ExtensionAware).extensions.configure("kotlinOptions", block)
}

/**
 * 버전 정보를 검증합니다.
 */
fun Project.validateVersions() {
    val compileSdk = findVersionCatalog().findVersion("compile_sdk").get().toString().toInt()
    val minSdk = findVersionCatalog().findVersion("min_sdk").get().toString().toInt()

    require(compileSdk >= minSdk) { "compileSdk($compileSdk) should be greater than or equal to minSdk($minSdk)" }
}
