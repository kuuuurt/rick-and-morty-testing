plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlinx-serialization")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    compileSdkVersion(29)

    defaultConfig {
        applicationId = "com.kuuuurt.rickandmorty"
        minSdkVersion(23)
        targetSdkVersion(29)
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled =  false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    sourceSets {
        getByName("androidTest") {
            java.srcDirs("src/sharedTest/java")
        }
        getByName("test") {
            java.srcDirs("src/sharedTest/java")
        }
    }

    buildFeatures.viewBinding = true
}

dependencies {
    val navigationVersion: String by rootProject.extra
    val kotlinVersion: String by rootProject.extra
    val ktorVersion = "1.4.1"
    val coroutinesVersion = "1.3.9"
    val fragmentVersion = "1.3.0-beta01"

    implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")
    implementation("androidx.core:core-ktx:1.3.2")
    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("androidx.fragment:fragment-ktx:$fragmentVersion")
    implementation("com.google.android.material:material:1.3.0-alpha03")
    implementation("androidx.navigation:navigation-fragment-ktx:$navigationVersion")
    implementation("androidx.navigation:navigation-ui-ktx:$navigationVersion")
    implementation("io.coil-kt:coil:1.0.0-rc3")

    implementation("io.ktor:ktor-client-android:$ktorVersion")
    implementation("io.ktor:ktor-client-serialization-jvm:$ktorVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.0.0-RC2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    
    testImplementation("junit:junit:4.13")
    testImplementation("io.ktor:ktor-client-mock-jvm:$ktorVersion")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion")

    debugImplementation("androidx.fragment:fragment-testing:$fragmentVersion")
    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")
    androidTestImplementation("androidx.navigation:navigation-testing:$navigationVersion")
    androidTestImplementation("com.schibsted.spain:barista:3.7.0")

}