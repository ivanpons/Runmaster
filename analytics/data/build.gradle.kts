plugins {
    alias(libs.plugins.runmaster.android.library)
}

android {
    namespace = "com.llimapons.analytics.data"
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)

    implementation(projects.core.database)
    implementation(projects.core.domain)
    implementation(projects.analytics.domain)

}