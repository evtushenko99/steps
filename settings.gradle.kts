pluginManagement {
    includeBuild("buildLogic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

rootProject.name = "Steps"
include(":app")

include(":core:designsystem")
include(":core:localdata")
include(":core:utils")
include(":core:test")

include(":feature:dailycounter")
include(":feature:statistic")
include(":feature:more")
include(":feature:stepcounterservice")
include(":feature:glonav")
