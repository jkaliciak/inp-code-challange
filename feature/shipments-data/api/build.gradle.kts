plugins {
    `android-library`
    `kotlin-android`
}

apply<AndroidLibraryPlugin>()

android {
    namespace = "pl.inpost.recruitmenttask.shipments.data.api"
}

dependencies {
    implementation(project(":common:data"))

    implementation(Dependencies.coreKtx)
    implementation(Dependencies.lifecycleRuntimeKtx)
    implementation(Dependencies.lifecycleViewModelCompose)
    implementation(Dependencies.lifecycleRuntimeCompose)
    implementation(Dependencies.activityCompose)
    implementation(Dependencies.material3)
    compose()
    hilt()
    room()
    implementation(Dependencies.hiltNavigationCompose)
//    implementation(Dependencies.kotlinxCollectionsImmutable) // collections compose treat as stable

    testImplementation(Dependencies.junit)

    androidTestImplementation(Dependencies.junitExt)
    androidTestImplementation(Dependencies.espressoCore)
}