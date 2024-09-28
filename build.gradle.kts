buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath(Dependencies.Plugins.hiltAgp)
        classpath(Dependencies.Plugins.ksp)
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.24")
    }
}