package com.example.convension

import com.example.convension.config.Plugins.ANDROID_LIB
import com.example.convension.config.Plugins.DAGGER_HILT_ANDROID
import com.example.convension.config.Plugins.KOTLIN_ANDROID
import com.example.convension.config.Plugins.KOTLIN_PLUGIN_COMPOSE
import com.example.convension.config.Plugins.KSP
import com.example.convension.config.configBuildType
import com.example.convension.config.configCompose
import com.example.convension.config.defaultConfig
import com.example.convension.config.kotlinOptions
import org.gradle.api.Plugin
import org.gradle.api.Project

class StepsAndroidGradlePlugin : Plugin<Project> {
    override fun apply(project: Project) {
        applyPlugins(project)
        setProjectConfig(project)
    }

    private fun applyPlugins(project: Project) {
        with(project.pluginManager) {
            apply(ANDROID_LIB)
            apply(KOTLIN_ANDROID)
            apply(DAGGER_HILT_ANDROID)
            apply(KSP)
            apply(KOTLIN_PLUGIN_COMPOSE)
        }
    }

    private fun setProjectConfig(project: Project) {
        project.defaultConfig()
        project.configCompose()
        project.configBuildType()
        project.kotlinOptions()
    }
}
