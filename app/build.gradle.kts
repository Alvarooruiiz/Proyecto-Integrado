plugins {
    alias(libs.plugins.androidApplication)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.proyectoalfari"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.proyectoalfari"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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

    packagingOptions {
        exclude ("META-INF/NOTICE.md")
        exclude ("META-INF/LICENSE.md")
        exclude ("META-INF/DEPENDENCIES")
        exclude ("META-INF/NOTICE")
        exclude ("META-INF/LICENSE")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation(platform("com.google.firebase:firebase-bom:32.8.1"))

    implementation("com.google.firebase:firebase-analytics")
    implementation ("com.google.firebase:firebase-auth")
    implementation ("com.google.firebase:firebase-firestore")
    implementation("com.google.firebase:firebase-database-ktx")

    implementation ("com.sun.mail:jakarta.mail:2.0.1")
    implementation ("com.sun.activation:jakarta.activation:2.0.1")

    implementation ("com.itextpdf:itext7-core:7.1.16")

    implementation ("com.github.bumptech.glide:glide:4.14.2")

    implementation ("com.google.zxing:core:3.4.1")
    implementation ("com.journeyapps:zxing-android-embedded:4.2.0")

    implementation ("androidx.core:core-splashscreen:1.0.0")

    implementation ("com.google.android.material:material:1.4.0")
    implementation ("androidx.drawerlayout:drawerlayout:1.1.1")
    implementation ("androidx.appcompat:appcompat:1.3.1")

    implementation ("com.google.android.material:material:1.6.0")
    implementation ("com.google.android.material:material:1.3.0")

    implementation ("androidx.constraintlayout:constraintlayout:2.1.0")
    implementation ("androidx.viewpager2:viewpager2:1.0.0")




    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.firebase.storage)
    implementation(libs.firebase.database)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}