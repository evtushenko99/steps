plugins {
    alias(libs.plugins.steps.android.library)
}

android {
    namespace = "com.example.glonav"
}

dependencies {

    // Core
    implementation(project(":core:designsystem"))
    implementation(project(":core:utils"))
    implementation(project(":core:localdata"))

    // Feature
    implementation(project(":feature:dailycounter"))
    implementation(project(":feature:more"))
    implementation(project(":feature:statistic"))
    implementation(project(":feature:stepcounterservice"))

    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)

    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.compose)
    debugImplementation(libs.bundles.compose.debug)

    implementation(libs.bundles.hilt)
    ksp(libs.bundles.hilt.ksp)

    androidTestImplementation(libs.bundles.ui.test)
}