import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.5.0"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("org.asciidoctor.convert") version "1.5.8"
    id("io.gitlab.arturbosch.detekt").version("1.16.0")
    id("jacoco")

    kotlin("jvm") version "1.5.10"
    kotlin("plugin.spring") version "1.5.10"
    kotlin("plugin.jpa") version "1.5.10"
}

group = "com.pintailconsultingllc"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

object DependencyVersions {
    const val KEYCLOAK_VERSION = "13.0.1"
    const val INFINISPAN_VERSION = "10.1.8.Final"
    const val RESTEASY_VERSION = "3.12.1.Final"
}

repositories {
    mavenCentral()
    maven { url = uri("https://repo.spring.io/snapshot") }
    maven { url = uri("https://repo.spring.io/milestone") }
}

extra["snippetsDir"] = file("build/generated-snippets")
extra["springCloudVersion"] = "2020.0.3-SNAPSHOT"
extra["infinispan.version"] = "10.1.8.Final"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.liquibase:liquibase-core")
    implementation("org.springframework.cloud:spring-cloud-starter-circuitbreaker-resilience4j")
    implementation("org.springframework.cloud:spring-cloud-starter-sleuth")
    implementation("org.springframework.kafka:spring-kafka")
    implementation("org.junit.jupiter:junit-jupiter:5.4.2")
    implementation("org.springdoc:springdoc-openapi-ui:1.5.5")
    implementation("org.springdoc:springdoc-openapi-data-rest:1.5.5")
    implementation("org.springdoc:springdoc-openapi-security:1.5.5")
    implementation("org.springdoc:springdoc-openapi-kotlin:1.5.5")
    implementation("io.github.microutils:kotlin-logging-jvm:2.0.6")
    implementation("com.opencsv:opencsv:5.4")
    // Keycloak implementation
    implementation("org.jboss.resteasy:resteasy-jackson2-provider:${DependencyVersions.RESTEASY_VERSION}")
    implementation(
        group = "org.keycloak",
        name = "keycloak-dependencies-server-all",
        version = DependencyVersions.KEYCLOAK_VERSION,
        ext = "pom"
    )
//    <exclusions>
//    <exclusion>
//    <groupId>org.slf4j</groupId>
//    <artifactId>slf4j-log4j12</artifactId>
//    </exclusion>
//    <exclusion>
//    <groupId>log4j</groupId>
//    <artifactId>log4j</artifactId>
//    </exclusion>
//    </exclusions>

    developmentOnly("org.springframework.boot:spring-boot-devtools")

//    runtimeOnly("io.micrometer:micrometer-registry-datadog")
//    runtimeOnly("org.postgresql:postgresql")
    runtimeOnly("com.h2database:h2")

    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(module = "mockito-core")
    }
    testImplementation("org.springframework.kafka:spring-kafka-test")
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("io.mockk:mockk:1.10.6")
    testImplementation("com.ninja-squad:springmockk:3.0.1")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}


tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

jacoco {
    toolVersion = "0.8.6"
    reportsDirectory.set(file("$buildDir/customJacocoReportDir"))
}

tasks.jacocoTestReport {
    reports {
        xml.isEnabled = false
        csv.isEnabled = false
        html.destination = file("${buildDir}/jacocoHtml")
    }
}

tasks.jacocoTestCoverageVerification {
    violationRules {
        rule {
            limit {
                minimum = "0.5".toBigDecimal()
            }
        }

        rule {
            enabled = false
            element = "CLASS"
            includes = listOf("org.gradle.*")

            limit {
                counter = "LINE"
                value = "TOTALCOUNT"
                maximum = "0.3".toBigDecimal()
            }
        }
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport) // report is always generated after tests run
}

tasks.jacocoTestReport {
    dependsOn(tasks.test) // tests are required to run before generating the report
}

