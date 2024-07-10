plugins {
    alias(libs.plugins.steps.android.library)
}

android {
    namespace = "com.example.stepcounterservice"
}

dependencies {

    implementation(project(":core:localdata"))
    implementation(project(":core:utils"))

    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)

    implementation(libs.bundles.hilt)
    implementation(libs.androidx.lifecycle.service)
    ksp (libs.bundles.hilt.ksp)

    androidTestImplementation(libs.bundles.ui.test)
}