plugins {
    alias(libs.plugins.runmaster.android.feature.ui)
}

android {
    namespace = "com.llimapons.analytics.presentation"
}

dependencies {
    implementation(projects.analytics.domain)
}