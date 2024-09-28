import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidLibraryPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        applyPlugins(project)
        setAndroidConfig(project)
    }

    private fun applyPlugins(project: Project) {
        project.apply {
//            plugin("android-library")
//            plugin("kotlin-android")
            plugin("kotlin-parcelize")
            plugin("kotlin-kapt")
            plugin("com.google.devtools.ksp")
            plugin("com.google.dagger.hilt.android")
        }
    }

    private fun setAndroidConfig(project: Project) {
        project.android().apply {
            compileSdk = AndroidConfig.compileSdk

            defaultConfig {
                minSdk = AndroidConfig.mindSdk
                targetSdk = AndroidConfig.targetSdk

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

            buildFeatures {
                compose = true
            }
            composeOptions {
                kotlinCompilerExtensionVersion = Versions.composeCompiler
            }
        }
    }

    private fun Project.android(): LibraryExtension =
        extensions.getByType(LibraryExtension::class.java)
}