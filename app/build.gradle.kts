plugins {
    alias(libs.plugins.steps.application.android.library)
}

android {
    namespace = "com.example.steps"

    defaultConfig {
        applicationId = "com.example.steps"
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