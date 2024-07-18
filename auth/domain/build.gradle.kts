plugins {
    alias(libs.plugins.runmaster.jvm.library)
}

dependencies {
    implementation(projects.core.domain)
}