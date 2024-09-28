import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension

class JvmLibraryPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        applyPlugins(project)
        setJvmConfig(project)
    }

    private fun applyPlugins(project: Project) {
        project.apply {
            plugin("java-library")
            plugin("org.jetbrains.kotlin.jvm")
            plugin("com.google.devtools.ksp")
        }
    }

    private fun setJvmConfig(project: Project) {
        project.jvm().apply {
            sourceCompatibility = Versions.javaVersion
            targetCompatibility = Versions.javaVersion
        }
    }

    private fun Project.jvm(): JavaPluginExtension =
        extensions.getByType(JavaPluginExtension::class.java)
}