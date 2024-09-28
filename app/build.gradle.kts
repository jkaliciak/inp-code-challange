plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("kotlin-parcelize")
}

android {
    namespace = "pl.inpost.recruitmenttask"
    compileSdk = AndroidConfig.compileSdk

    defaultConfig {
        applicationId = "pl.inpost.recruitmenttask"
        minSdk = AndroidConfig.mindSdk
        targetSdk = AndroidConfig.targetSdk
        versionCode = AndroidConfig.versionCode
        versionName = AndroidConfig.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = Versions.javaVersion
        targetCompatibility = Versions.javaVersion
    }
    kotlinOptions {
        jvmTarget = Versions.javaVersion.toString()
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.composeCompiler
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    kapt {
        correctErrorTypes = true
    }
}

dependencies {
    implementation(project(":common:theme"))
    implementation(project(":feature:shipments-ui"))
    implementation(project(":feature:shipments-data:api"))
    implementation(project(":feature:shipments-data:impl"))
    implementation(project(":feature:shipments-domain:api"))
    implementation(project(":feature:shipments-domain:impl"))

    implementation(Dependencies.coreKtx)
    implementation(Dependencies.lifecycleRuntimeKtx)
    implementation(Dependencies.lifecycleViewModelCompose)
    implementation(Dependencies.lifecycleRuntimeCompose)
    implementation(Dependencies.activityCompose)
    implementation(Dependencies.material3)
    compose()
    hilt()
    implementation(Dependencies.hiltNavigationCompose)
    implementation(Dependencies.kotlinxCollectionsImmutable) // collections compose treat as stable

    testImplementation(Dependencies.junit)
    testImplementation(Dependencies.roomTesting)

    androidTestImplementation(Dependencies.junitExt)
    androidTestImplementation(Dependencies.espressoCore)
}
