import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.4.3"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.4.30"
    kotlin("plugin.spring") version "1.4.30"
    kotlin("plugin.jpa") version "1.4.30"
}

group = "com.pintailconsultingllc"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-data-rest")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.liquibase:liquibase-core")
    implementation("org.springframework.kafka:spring-kafka")
    implementation("org.junit.jupiter:junit-jupiter:5.4.2")
    implementation("org.springdoc:springdoc-openapi-ui:1.5.5")
    implementation("org.springdoc:springdoc-openapi-data-rest:1.5.5")
    implementation("org.springdoc:springdoc-openapi-security:1.5.5")
    implementation("org.springdoc:springdoc-openapi-kotlin:1.5.5")
    implementation("io.github.microutils:kotlin-logging-jvm:2.0.6")
    implementation("com.opencsv:opencsv:5.4")

    developmentOnly("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("org.postgresql:postgresql")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(module = "mockito-core")
    }
    testImplementation("org.springframework.kafka:spring-kafka-test")
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("io.mockk:mockk:1.10.6")
    testImplementation("com.ninja-squad:springmockk:3.0.1")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
