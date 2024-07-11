plugins {
    alias(libs.plugins.steps.android.library)
}

android {
    namespace = "com.example.localdata"
}

dependencies {

    implementation(project(":core:utils"))
    implementation(project(":core:test"))

    implementation(libs.core.ktx)

    implementation(libs.bundles.hilt)
    implementation(libs.bundles.room)
    implementation(libs.datastore)

    ksp(libs.bundles.hilt.ksp)
    ksp(libs.bundles.room.ksp)

    testImplementation(libs.bundles.unit.test)

    androidTestImplementation(libs.bundles.ui.test)
    androidTestImplementation(libs.room.test)
}