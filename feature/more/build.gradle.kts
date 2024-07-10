plugins {
    alias(libs.plugins.steps.android.library)
}

android {
    namespace = "com.example.more"
}

dependencies {

    implementation(project(":core:designsystem"))
    implementation(project(":core:localdata"))
    implementation(project(":core:utils"))
    implementation(project(":core:test"))

    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)

    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.compose)
    debugImplementation(libs.bundles.compose.debug)


    implementation(libs.coil)

    implementation(libs.bundles.hilt)
    ksp(libs.bundles.hilt.ksp)
    androidTestImplementation(libs.hilt.android.test)

    testImplementation(libs.bundles.unit.test)
    androidTestImplementation(libs.bundles.ui.test)
    androidTestImplementation(libs.bundles.compose.test)
}