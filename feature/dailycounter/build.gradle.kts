plugins {
    alias(libs.plugins.steps.android.library)
}

android {
    namespace = "com.example.dailycounter"
}

dependencies {

    implementation(project(":core:designsystem"))
    implementation(project(":core:localdata"))
    implementation(project(":core:utils"))

    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)

    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.compose)
    debugImplementation(libs.bundles.compose.debug)
    androidTestImplementation(libs.bundles.compose.test)

    implementation(libs.bundles.hilt)
    ksp (libs.bundles.hilt.ksp)

    androidTestImplementation(libs.bundles.ui.test)
}