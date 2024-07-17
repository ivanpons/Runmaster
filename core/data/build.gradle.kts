plugins {
    alias(libs.plugins.runmaster.android.library)
}

android {
    namespace = "com.llimapons.core.data"
}

dependencies {
    implementation(libs.timber)
    implementation(libs.bundles.koin)

    implementation(projects.core.domain)
    implementation(projects.core.database)
}