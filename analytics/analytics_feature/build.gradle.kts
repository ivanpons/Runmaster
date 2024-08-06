plugins {
    alias(libs.plugins.runmaster.android.dynamic.feature)
}
android {
    namespace = "com.llimapons.analytics.analytics_feature"
}

dependencies {
    implementation(project(":app"))
    implementation(libs.androidx.navigation.compose)

    api(projects.analytics.presentation)
    implementation(projects.analytics.domain)
    implementation(projects.analytics.data)
    implementation(projects.core.database)

}