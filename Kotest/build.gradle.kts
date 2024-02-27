plugins {
    kotlin("jvm") version "1.9.22"
}

group = "com.kotlin"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val koTestCoreVersion = "5.8.0"

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    // Kotest
    testImplementation("io.kotest:kotest-runner-junit5:$koTestCoreVersion")
    testImplementation("io.kotest:kotest-assertions-core:$koTestCoreVersion")
    testImplementation("io.kotest:kotest-property:$koTestCoreVersion")
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}