plugins {
    alias(libs.plugins.steps.simple.android.library)
}

android {
    namespace = "com.example.utils"
}

dependencies {
    implementation(libs.core.ktx)
    implementation(libs.datastore)
}