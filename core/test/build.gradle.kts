plugins {
    alias(libs.plugins.steps.simple.android.library)
}

android {
    namespace = "com.example.test"
}

dependencies {

    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)

    implementation(libs.bundles.unit.test)
}