plugins {
    kotlin("jvm") version "1.6.10"
    java
    id("com.github.johnrengelman.shadow") version "7.1.0" // Füge das Shadow Plugin hinzu
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-netty:1.6.8") // Ktor server
    implementation("io.ktor:ktor-server-core:1.6.8") // Ktor core
    implementation("io.ktor:ktor-html-builder:1.6.8") // Optional, for HTML responses
    implementation("ch.qos.logback:logback-classic:1.2.6") // Logging

    implementation("com.squareup.okhttp3:okhttp:4.9.2")
}

tasks.shadowJar {
    manifest {
        attributes["Main-Class"] = "com.trivaris.MainKt" // Adjust to your package name
    }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}
