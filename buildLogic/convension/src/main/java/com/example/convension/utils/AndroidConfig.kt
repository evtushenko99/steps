package com.example.convension.utils

import com.example.convension.config.findLibVersion
import org.gradle.api.Project

/**
 * Константы для всех `android` модулей
 */
enum class AndroidConfig(private val key: String) {
    /**
     * `Compile` версия `SDK`
     */
    ANDROID_COMPILE_SDK_VERSION("androidCompileSdk"),

    /**
     * `Target` версия `SDK`
     */
    ANDROID_TARGET_SDK_VERSION("androidTargetSdk"),

    /**
     * Минимальная версия `SDK`
     */
    ANDROID_MIN_SDK_VERSION("androidMinSdk"),

    /**
     * Версия компилятора котлин для Compose
     */
    KOTLIN_COMPILER_EXT_VERSION("kotlinCompilerExtensionVersion");


    /**
     * Получить версию из Version Catalog проекта [project]
     */
    fun getInt(project: Project): Int = project.findLibVersion(key).toInt()

    /**
     * Получить версию в виде стринги из Version Catalog проекта [project]
     */
    fun getString(project: Project): String = project.findLibVersion(key)
}
