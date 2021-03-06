buildscript {
    dependencies {
        classpath("com.google.dagger:hilt-android-gradle-plugin:${com.example.buildsrc.Versions.hilt}")
    }
}
plugins {
    id("com.android.application") version "7.1.2" apply false
    id("com.android.library") version "7.1.2" apply false
    id("org.jetbrains.kotlin.android") version "1.6.10" apply false
}
tasks.register("clean", Delete::class.java) {
    delete(rootProject.buildDir)
}