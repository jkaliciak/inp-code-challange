import org.gradle.api.artifacts.dsl.DependencyHandler

object Dependencies {

    object Plugins {
        const val hiltAgp = "com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt}"
        const val ksp =
            "com.google.devtools.ksp:com.google.devtools.ksp.gradle.plugin:${Versions.ksp}"
    }

    const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutinesCore}"

    const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
    const val lifecycleRuntimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}"
    const val lifecycleViewModelCompose =
        "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.lifecycle}"
    const val lifecycleRuntimeCompose =
        "androidx.lifecycle:lifecycle-runtime-compose:${Versions.lifecycle}"
    const val activityCompose = "androidx.activity:activity-compose:${Versions.activityCompose}"
    const val material3 = "com.google.android.material:material:${Versions.material3}"

    const val roomRuntime = "androidx.room:room-runtime:${Versions.room}"
    const val roomKtx = "androidx.room:room-ktx:${Versions.room}"
    const val roomCompiler = "androidx.room:room-compiler:${Versions.room}" // ksp

    const val moshi = "com.squareup.moshi:moshi-kotlin:${Versions.moshi}"

    const val composeBom =
        "androidx.compose:compose-bom:${Versions.composeBom}" // platform, androidTestImplementation platform
    const val composeRuntime = "androidx.compose.runtime:runtime"
    const val composeUi = "androidx.compose.ui:ui"
    const val composeUiGraphics = "androidx.compose.ui:ui-graphics"
    const val composeUiToolingPreview = "androidx.compose.ui:ui-tooling-preview"
    const val composeMaterial3 = "androidx.compose.material3:material3"

    const val kotlinxCollectionsImmutable =
        "org.jetbrains.kotlinx:kotlinx-collections-immutable:${Versions.kotlinxCollectionsImmutable}"

    const val hiltNavigationCompose =
        "androidx.hilt:hilt-navigation-compose:${Versions.hiltNavigationCompose}"
    const val hiltAndroid = "com.google.dagger:hilt-android:${Versions.hilt}"
    const val hiltCompiler = "com.google.dagger:hilt-compiler:${Versions.hilt}" // kapt

    const val junit = "junit:junit:${Versions.junit}" // testImplementation
    const val roomTesting = "androidx.room:room-testing:${Versions.room}" // testImplementation
    const val junitExt = "androidx.test.ext:junit:${Versions.junitExt}" // androidTestImplementation
    const val espressoCore =
        "androidx.test.espresso:espresso-core:${Versions.espresso}" // androidTestImplementation
    const val composeUiTestJunit4 =
        "androidx.compose.ui:ui-test-junit4" // androidTestImplementation

    const val composeUiTooling = "androidx.compose.ui:ui-tooling" // debugImplementation
    const val composeUiTestManifest = "androidx.compose.ui:ui-test-manifest" // debugImplementation
}

fun DependencyHandler.room() {
    implementation(platform(Dependencies.composeBom))
    implementation(Dependencies.roomRuntime)
    implementation(Dependencies.roomKtx)
    ksp(Dependencies.roomCompiler)
    testImplementation(Dependencies.roomTesting)
}

fun DependencyHandler.hilt() {
    implementation(Dependencies.hiltAndroid)
    kapt(Dependencies.hiltCompiler)
}

fun DependencyHandler.compose() {
    implementation(platform(Dependencies.composeBom))
    implementation(Dependencies.composeRuntime)
    implementation(Dependencies.composeUi)
    implementation(Dependencies.composeUiGraphics)
    implementation(Dependencies.composeUiToolingPreview)
    implementation(Dependencies.composeMaterial3)
    androidTestImplementation(platform(Dependencies.composeBom))
    androidTestImplementation(Dependencies.composeUiTestJunit4)
    debugImplementation(Dependencies.composeUiTooling)
    debugImplementation(Dependencies.composeUiTestManifest)
}

fun DependencyHandler.coroutines() {
    implementation(Dependencies.coroutinesCore)
}