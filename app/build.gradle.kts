import com.github.triplet.gradle.androidpublisher.ReleaseStatus
import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.steps.application.android.library)
    alias(libs.plugins.triplet.play)
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

    // Specifies one flavor dimension.
    flavorDimensions += "market"
    productFlavors {
        create("google") {
            dimension = "market"
            versionName = android.defaultConfig.versionName + "-Google"
        }

        create("huawei") {
            dimension = "market"
            versionName = android.defaultConfig.versionName + "-Huawei"
        }
    }

    buildTypes {
        debug {
            isDebuggable = true
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
        }
        create("beta") {
            isDebuggable = true
            applicationIdSuffix = ".beta"
            versionNameSuffix = "-beta"
        }
        release {
            isDebuggable = false
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
    }

}

play {
    serviceAccountCredentials.set(file("keystores/serviceStepsKey.json"))
    enabled.set(false)
    track.set("release")
    userFraction.set(0.1) // 10%
    defaultToAppBundles.set(true)
    releaseStatus.set(ReleaseStatus.IN_PROGRESS)
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