plugins {
    alias(libs.plugins.runmaster.android.feature.ui)
}

android {
    namespace = "com.llimapons.auth.presentation"
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.auth.domain)
}