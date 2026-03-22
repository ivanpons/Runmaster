plugins {
    alias(libs.plugins.runmaster.android.library)
    alias(libs.plugins.runmaster.android.room)
}

android {
    namespace = "com.llimapons.analytics.data"
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.bundles.koin)

    implementation(projects.core.database)
    implementation(projects.core.domain)
    implementation(projects.analytics.domain)

}