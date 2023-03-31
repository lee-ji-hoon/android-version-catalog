package convention

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType

/**
 * Gradle 프로젝트에서 사용되는 함수로, "libs"라는 이름으로 지정된 버전 카탈로그를 가져옵니다.
 * @return "libs" 이름으로 지정된 VersionCatalog 객체
 */
fun Project.findVersionCatalog(): VersionCatalog =
    extensions.getByType<VersionCatalogsExtension>().named("libs")