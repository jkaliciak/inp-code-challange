plugins {
    `android-library`
    `kotlin-android`
}

apply<AndroidLibraryPlugin>()

android {
    namespace = "pl.inpost.recruitmenttask.shipments.data.impl"
}

dependencies {
    implementation(project(":common:data"))
    implementation(project(":feature:shipments-data:api"))

    implementation(Dependencies.coreKtx)
    implementation(Dependencies.lifecycleRuntimeKtx)
    implementation(Dependencies.lifecycleViewModelCompose)
    implementation(Dependencies.lifecycleRuntimeCompose)
    implementation(Dependencies.activityCompose)
    implementation(Dependencies.material3)
    coroutines()
    compose()
    room()
    hilt()
    implementation(Dependencies.hiltNavigationCompose)
    implementation(Dependencies.kotlinxCollectionsImmutable) // collections compose treat as stable

    implementation(Dependencies.moshi)

    unitTest()

    androidTestImplementation(Dependencies.junitExt)
    androidTestImplementation(Dependencies.espressoCore)
}