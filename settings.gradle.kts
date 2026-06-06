pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolution {
    @Suppress("UnstableApiUsage")
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}

rootProject.name = "OpenLedger"
include(":app")
include(":core:core-common")
include(":core:core-model")
include(":core:core-database")
include(":core:core-data")
include(":core:core-domain")
include(":feature:feature-home")
include(":feature:feature-transaction")
include(":feature:feature-statistics")
include(":feature:feature-settings")
include(":feature:feature-backup")