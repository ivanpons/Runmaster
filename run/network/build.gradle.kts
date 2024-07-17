plugins {
    alias(libs.plugins.runmaster.android.library)
}

android {
    namespace = "com.llimapons.run.network"
}

dependencies {
    implementation(libs.bundles.koin)

    implementation(projects.core.domain)
    implementation(projects.core.data)
}