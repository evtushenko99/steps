package com.example.convension.config

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.LibraryExtension
import com.example.convension.utils.AndroidConfig
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile


/**
 * Получить версию из Version Catalog проекта project
 */
internal fun Project.findLibVersion(name: String) = extensions
    .getByType(VersionCatalogsExtension::class.java)
    .named("libs")
    .findVersion(name)
    .get()
    .requiredVersion

internal fun Project.appExtension(): ApplicationExtension {
    return extensions.getByType(ApplicationExtension::class.java)
}

internal fun Project.kotlinOptions() {
    tasks.withType(KotlinCompile::class.java).configureEach {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
            suppressWarnings.set(true)
        }
    }
}

internal fun Project.defaultConfig(isLibExtension: Boolean = true) {
    val ext = if (isLibExtension) {
        libExtension()
    } else {
        appExtension().apply {
            defaultConfig {
                targetSdk = AndroidConfig.ANDROID_TARGET_SDK_VERSION.getInt(project)
                versionCode = 1
                versionName = "1.0"
            }
        }
    }
    ext.apply {
        compileSdk = AndroidConfig.ANDROID_COMPILE_SDK_VERSION.getInt(project)

        defaultConfig {
            minSdk = AndroidConfig.ANDROID_MIN_SDK_VERSION.getInt(project)
            lint.targetSdk = AndroidConfig.ANDROID_TARGET_SDK_VERSION.getInt(project)

            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            vectorDrawables {
                useSupportLibrary = true
            }
        }

        packaging {
            resources {
                excludes += "META-INF/LICENSE.md"
                excludes += "META-INF/LICENSE-notice.md"
            }
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
        }
    }
}

internal fun Project.configCompose() {
    libExtension().apply {
        composeOptions {
            kotlinCompilerExtensionVersion =
                AndroidConfig.KOTLIN_COMPILER_EXT_VERSION.getString(project)
        }

        buildFeatures {
            compose = true
        }
    }
}

internal fun Project.configBuildType() {
    extensions.getByType(CommonExtension::class.java).apply {
        buildTypes {
            getByName("release") {
                isMinifyEnabled = false
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            }
        }
    }
}

private fun Project.libExtension(): LibraryExtension {
    return extensions.getByType(LibraryExtension::class.java)
}
