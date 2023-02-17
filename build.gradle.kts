plugins {
    kotlin("jvm") version "1.8.10"
    application
}

group = "griffio"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    google()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    implementation("com.github.skydoves:sandwich:1.3.3")

    implementation("com.squareup.moshi:moshi:1.14.0")
    implementation("com.squareup.moshi:moshi-kotlin:1.14.0")
    implementation("com.squareup.retrofit2:converter-moshi:2.9.0")

    testImplementation(kotlin("test"))
}

kotlin {
    jvmToolchain(14)
}

tasks.test {
    useJUnitPlatform()
}

application {
    mainClass.set("griffio.MainKt")
}
