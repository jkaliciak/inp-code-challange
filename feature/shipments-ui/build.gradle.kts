plugins {
    `android-library`
    `kotlin-android`
}

apply<AndroidLibraryPlugin>()

android {
    namespace = "pl.inpost.recruitmenttask.shipments.ui"
}

dependencies {
    implementation(project(":common:data"))
    implementation(project(":common:domain"))
    implementation(project(":common:ui"))
    implementation(project(":common:theme"))
    implementation(project(":common:translation"))
    implementation(project(":feature:shipments-data:api"))
    implementation(project(":feature:shipments-domain:api"))
    implementation(project(":feature:shipments-domain:impl"))

    implementation(Dependencies.coreKtx)
    implementation(Dependencies.lifecycleRuntimeKtx)
    implementation(Dependencies.lifecycleViewModelCompose)
    implementation(Dependencies.lifecycleRuntimeCompose)
    implementation(Dependencies.activityCompose)
    implementation(Dependencies.material3)
    coroutines()
    compose()
    hilt()
    implementation(Dependencies.hiltNavigationCompose)
    implementation(Dependencies.kotlinxCollectionsImmutable) // collections compose treat as stable

    unitTest()

    androidTestImplementation(Dependencies.junitExt)
    androidTestImplementation(Dependencies.espressoCore)
}