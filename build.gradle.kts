buildscript {
    val kotlinVersion by extra("1.4.10")
    val navigationVersion by extra("2.3.0")
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:4.1.0-rc03")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
        classpath("org.jetbrains.kotlin:kotlin-serialization:$kotlinVersion")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$navigationVersion")
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}