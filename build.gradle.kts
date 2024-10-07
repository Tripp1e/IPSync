plugins {
    kotlin("jvm") version "1.6.10"
    java
    id("com.github.johnrengelman.shadow") version "7.1.0" // Füge das Shadow Plugin hinzu
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.squareup.okhttp3:okhttp:4.9.2")
}

tasks.shadowJar {
    manifest {
        attributes["Main-Class"] = "main.MainKt"
    }
}
