package convention

import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.provider.Provider
import java.util.Optional

/**
 * 
 */

/**
 * "implementation" 구성 요소에 의존성을 추가합니다.
 * @param dependencyNotation 의존성을 지정하는 Provider 객체
 */
internal fun <T> DependencyHandler.implementation(
    dependencyNotation: Optional<Provider<T>>
): Dependency? = add("implementation", dependencyNotation.get())

/**
 * "debugImplementation" 구성 요소에 의존성을 추가합니다.
 * @param dependencyNotation 의존성을 지정하는 Provider 객체
 */
internal fun <T> DependencyHandler.debugImplementation(
    dependencyNotation: Optional<Provider<T>>
): Dependency? = add("debugImplementation", dependencyNotation.get())

/**
 * "kapt" 구성 요소에 의존성을 추가합니다.
 * @param dependencyNotation 의존성을 지정하는 Provider 객체
 */
internal fun <T> DependencyHandler.kapt(
    dependencyNotation: Optional<Provider<T>>
): Dependency? = add("kapt", dependencyNotation.get())

/**
 * "api" 구성 요소에 의존성을 추가합니다.
 * @param dependencyNotation 의존성을 지정하는 Provider 객체
 */
internal fun <T> DependencyHandler.api(
    dependencyNotation: Provider<T>
): Dependency? = add("api", dependencyNotation)