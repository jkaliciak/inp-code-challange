plugins {
    `android-library`
    `kotlin-android`
}

apply<AndroidLibraryPlugin>()

android {
    namespace = "pl.inpost.recruitmenttask.shipments.domain.impl"
}

dependencies {
    implementation(project(":common:data"))
    implementation(project(":common:domain"))
    implementation(project(":feature:shipments-data:api"))
    implementation(project(":feature:shipments-domain:api"))

    coroutines()
    compose()
    hilt()
}