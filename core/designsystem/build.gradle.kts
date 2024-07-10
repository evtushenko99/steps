plugins {
    alias(libs.plugins.steps.android.library)
}

android {
    namespace = "com.example.designsystem"
}

dependencies {
    implementation(project(":core:utils"))
    implementation(project(":core:localdata"))

    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)

    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.compose)

    debugImplementation(libs.bundles.compose.debug)
    androidTestImplementation(libs.bundles.compose.test)

    implementation(libs.bundles.hilt)
    ksp(libs.bundles.hilt.ksp)

    androidTestImplementation(libs.bundles.ui.test)
}