plugins {
    alias(libs.plugins.runmaster.android.library)
}

android {
    namespace = "com.llimapons.core.database"
}

dependencies {
    implementation(libs.org.mongodb.bson)
    implementation(libs.bundles.koin)

    implementation(projects.core.domain)
}