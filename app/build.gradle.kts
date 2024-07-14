import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.steps.application.android.library)
}

val keystorePropertiesFile = rootProject.file("keystore.properties")
val keystoreProperties = Properties()
keystoreProperties.load(FileInputStream(keystorePropertiesFile))

android {
    namespace = "com.example.steps"

    defaultConfig {
        applicationId = "com.example.steps"
    }

    signingConfigs {
        create("release") {
            keyAlias = keystoreProperties["keyAlias"] as String
            keyPassword = keystoreProperties["keyPassword"] as String
            storeFile = file(keystoreProperties["storeFile"] as String)
            storePassword = keystoreProperties["keystorePassword"] as String
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
    }

}


dependencies {

    implementation(project(":feature:more"))
    implementation(project(":feature:stepcounterservice"))
    implementation(project(":feature:glonav"))

    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)

    implementation(libs.bundles.hilt)
    ksp(libs.bundles.hilt.ksp)
}