// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript{
    dependencies {
        classpath ("org.jetbrains.kotlin:kotlin-serialization:1.6.10")
    }
}
plugins {
    id("com.android.application") version "8.1.4" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    id("com.google.dagger.hilt.android") version "2.46.1" apply false
}