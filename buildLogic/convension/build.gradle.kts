plugins {
    `kotlin-dsl`
}

dependencies {
    compileOnly(libs.android.gradle.plugin)
    compileOnly(libs.kotlin.gradle.plugin)
}

gradlePlugin {
    plugins {
        register("StepsAndroidGradlePlugin") {
            id = "steps.android.library"
            implementationClass = "com.example.convension.StepsAndroidGradlePlugin"
        }

        register("StepsSimpleAndroidGradlePlugin") {
            id = "steps.simple.android.library"
            implementationClass = "com.example.convension.StepsSimpleAndroidGradlePlugin"
        }

        register("StepsApplicationAndroidGradlePlugin") {
            id = "steps.application.android.library"
            implementationClass = "com.example.convension.StepsApplicationAndroidGradlePlugin"
        }
    }
}