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

  /* productFlavors {
        google {
            dimension = "default"
            versionName = android.defaultConfig.versionName + "-Google"
        }
    }*/

    buildTypes {
        debug {
            isDebuggable = true
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
        }
     /*   getByName("beta") {
            applicationIdSuffix = ".beta"
            versionNameSuffix = "-beta"
        }*/
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
    enabled.set(false)
    track.set("production")
    userFraction.set(0.1) // 10%
    defaultToAppBundles.set(true)
    releaseStatus.set(ReleaseStatus.DRAFT)
    serviceAccountCredentials.set(file("google-play.json"))
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