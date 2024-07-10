package com.example.convension

import com.example.convension.config.Plugins.ANDROID_APP
import com.example.convension.config.Plugins.DAGGER_HILT_ANDROID
import com.example.convension.config.Plugins.KOTLIN_ANDROID
import com.example.convension.config.Plugins.KSP
import com.example.convension.config.configBuildType
import com.example.convension.config.defaultConfig
import com.example.convension.config.kotlinOptions
import org.gradle.api.Plugin
import org.gradle.api.Project

class StepsApplicationAndroidGradlePlugin : Plugin<Project> {
    override fun apply(project: Project) {
        applyPlugins(project)
        setProjectConfig(project)
    }

    private fun applyPlugins(project: Project) {
        with(project.pluginManager) {
            apply(ANDROID_APP)
            apply(KOTLIN_ANDROID)
            apply(DAGGER_HILT_ANDROID)
            apply(KSP)
        }
    }

    private fun setProjectConfig(project: Project) {
        project.defaultConfig(isLibExtension = false)
        project.configBuildType()
        project.kotlinOptions()
    }
}
