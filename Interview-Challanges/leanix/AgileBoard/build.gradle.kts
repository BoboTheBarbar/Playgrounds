import net.pwall.json.kotlin.codegen.gradle.JSONSchemaCodegenPlugin

plugins {
    id("org.springframework.boot") version "3.3.1"
    id("io.spring.dependency-management") version "1.1.5"
    kotlin("jvm") version "1.9.24"
    kotlin("plugin.spring") version "1.9.24"
    kotlin("plugin.serialization") version "1.9.24"
}

group = "de.leanix"
version = "0.0.1-SNAPSHOT"

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

repositories {
    mavenCentral()
}

val kotestVersion = "5.9.1"
val kotestSpringExtensionVersion = "1.1.3"
val ktorVersion = "2.3.11"
val mockkVersion = "1.12.0"
val postgresVersion = "42.7.3"
val kotlinxSerialization = "1.7.1"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    // Serialization https://mvnrepository.com/artifact/org.jetbrains.kotlinx/kotlinx-serialization-json-jvm

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json-jvm:$kotlinxSerialization")

    // H2
    runtimeOnly("com.h2database:h2")

    // Postgres
    runtimeOnly("org.postgresql:postgresql:$postgresVersion")

    // Junit
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")

    // Spring Boot Test
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    // Kotest
    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
    testImplementation("io.kotest:kotest-property:$kotestVersion")
    testImplementation("io.kotest.extensions:kotest-extensions-spring:$kotestSpringExtensionVersion")

    // Mockk
    testImplementation("io.mockk:mockk:$mockkVersion")

    // JUnit Platform Launcher
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {  // json-kotlin-schema-codegen
        classpath("net.pwall.json:json-kotlin-gradle:0.107")
    }
}

apply<JSONSchemaCodegenPlugin>()

sourceSets.main {   // json-kotlin-schema-codegen
    java.srcDirs("build/generated-sources/kotlin")
}
