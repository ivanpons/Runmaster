plugins {
    alias(libs.plugins.runmaster.android.library)
    alias(libs.plugins.runmaster.jvm.ktor)
}

android {
    namespace = "com.llimapons.auth.data"
}

dependencies {
    implementation(libs.bundles.koin)

    implementation(projects.auth.domain)
    implementation(projects.core.domain)
    implementation(projects.core.data)
}