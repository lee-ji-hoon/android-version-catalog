# android-version-catalog

## Android Version  관리하는 방법

### ext 를 통한 관리

```kotlin
// root build.gradle
buildscript {
    ext {
        nav_version = '2.5.2'
    }
}

// module build.gradle
dependencies {
    implementation "androidx.navigation:navigation-fragment-ktx:$nav_version"
    implementation "androidx.navigation:navigation-ui-ktx:$nav_version"
}
```

- Gradle에서 라이브러리 업데이트 가능 정보를 바로바로 노출 시킵니다.

<img width="1035" alt="Untitled" src="https://user-images.githubusercontent.com/53300830/224488299-c80b4fe6-d3f9-4748-985a-d23f61a8050b.png">

jetpack navigation이 업데이트 버전이 있다고 바로바로 노출을 시켜준다.

### buildSrc 를 통한 관리

- 플러그인 작성이 편리하다.
- 자동완성 및 오류코드 강조, 코드 탐색
- 익숙한 Kotlin 언어를 사용한다.

**단점**

- 코드상에서 버전을 하나만 바꿔도 캐시를 무시하고 전체를 다시 빌드한다는 문제점이 존재한다.
- 라이브러리 업데이트 정보를 알 수 없다는 단점이 존재한다.
    - 물론 별도의 라이브러리를 사용하면 되지만 내부에서 지원을 해주지 않는다는 것은 큰 단점이다.

### version catalog 를 통한 관리

- bundle 형태로 묶어서 관리하기 편리하다.
    - buildSrc에서도 가능하지만 코틀린 문법을 많이 추가해야하고 신경쓸게 좀 있다.

**단점**

- 라이브러리 업데이트 정보를 알 수 없다.
    - 별도의 라이브러리로 해결이 가능하긴하지만 애매하다.
    - 이 부분이 엄청 중요하게 생각을 하는데 third-party로 해결이 되니까 상관이 없는걸까?

## Version Catalog

> 안드로이드 운영체제의 버전과 해당 버전에서 지원되는 API 레벨, 새로운 기능, 변경된 동작, 보안 업데이트 및 기타 정보를 제공한다.

**Gradle 7.4부터는 Version Catalog를 정식 지원하는데 대부분의 최근 프로젝트는 7.4를 넘어서 7.5를 사용중이라고 판단을 하고 넘어가겠습니다!**

### toml 생성

```toml
[versions]
kotlin = "1.6.21"

android-gradle = "7.2.1"
androidx-core = "1.8.0"
androidx-activity = "1.5.0"
androidx-navigation = "2.4.2"

compose = "1.2.0-beta03"
material3 = "1.0.0-alpha14"

[libraries]
kotlin-plugin = { module = "org.jetbrains.kotlin:kotlin-gradle-plugin", version.ref = "kotlin" }

android-gradle = { module = "com.android.tools.build:gradle", version.ref = "android.gradle" }
androidx-core-ktx = { module = "androidx.core:core-ktx", version.ref = "androidx-core" }

androidx-activity-compose = { module = "androidx.activity:activity-compose", version.ref = "androidx-activity" }
androidx-compose-ui-core = { module = "androidx.compose.ui:ui", version.ref = "compose" }
androidx-compose-ui-tooling-core = { module = "androidx.compose.ui:ui-tooling", version.ref = "compose" }
androidx-compose-ui-tooling-preview = { module = "androidx.compose.ui:ui-tooling-preview", version.ref = "compose" }
androidx-compose-material3 = { module = "androidx.compose.material3:material3", version.ref = "material3" }
androidx-compose-navigation = { module = "androidx.navigation:navigation-compose", version.ref = "androidx-navigation" }

[bundles]
compose = ["androidx-compose-ui-core", "androidx-compose-ui-tooling-core", "androidx-compose-ui-tooling-preview", "androidx-compose-material3", "androidx-activity-compose", "androidx-compose-navigation"]
```

- `versions`   : 라이브러리들의 버전
- `libraries` : 라이브러리 의존성
- `bundles`     : 라이브러리들을 묶어서 한 번에 사용이 가능하다.

<details>
<summary>간단하게만 알아보는 toml 문법</summary>
<div markdown="1">

### Toml 파일이란?

> Tom’s Obvious, Minimal Language의 약자로 구조화된 데이터를 저장하는 데 사용하는 파일 형식이다.
> 
- 텍스트 파일로 작성되며, 주로 소프트웨어 설정 파일이나 구성 파일로 사용이 된다.

### Toml 문법

1. Key-Value 쌍: Toml 파일은 Key-Value 쌍으로 구성됩니다. 
    - Key는 문자열이며, Value는 문자열, 숫자, 불리언, 배열, 인라인 테이블 등의 값으로 구성될 수 있습니다.
2. 섹션: Toml 파일은 섹션으로 구성될 수 있습니다. 섹션은 대괄호(**`[ ]`**)로 묶인 문자열로 정의됩니다. 
    - 섹션 내에는 Key-Value 쌍이 포함될 수 있습니다.
3. 주석: Toml 파일은 주석을 포함할 수 있습니다. 주석은 해시 기호(**`#`**)로 시작하며, 줄 끝까지 계속됩니다.

```toml
# 예시 Toml 파일
title = "Toml 파일 예시"

[database]
server = "localhost"
port = 12345
enabled = true

[servers]

[servers.alpha]
ip = "10.0.0.1"
dc = "서울"

[servers.beta]
ip = "10.0.0.2"
dc = "도쿄"
```

- `title`과 `database`라는 Key-Value 쌍과 `servers`라는 섹션이 포함이 돼 있다.
- `servers` 섹션
    - `servers.alpha`와 `servers.beta`라는 섹션이 포함되어 있으며, 각각에는 `ip`와 `dc`
    라는 Key-Value 쌍이 포함돼 있습니다.

</div>
</details>

### 실제로 사용해보자!

```kotlin
// root build gradle
buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath(libs.android.gradle)
        classpath(libs.kotlin.plugin)
    }
}
```

```kotlin
// module build gradle
dependencies {
		// 하나씩 추가
    implementation(libs.androidx.compose.ui.core)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.activity.compose)

    // 번들로 일괄 추가
    implementation(libs.bundles.compose)
}
```

- 작성을 하다보면 알 수 있는 것이 자동완성을 지원해준다
- bundles로 편하게 묶어서 한 번에 편리하게 사용 가능
- 이정도?..
