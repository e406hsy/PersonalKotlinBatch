import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.6.1"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.6.0"
    kotlin("plugin.spring") version "1.6.0"
}

group = "com.soonyong"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    /** kotlin **/
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    /** jackson kotlin **/
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.0")

    /** spring **/
    implementation("org.springframework.boot:spring-boot-starter-batch")

    /** spring dev tool **/
    developmentOnly("org.springframework.boot:spring-boot-devtools")

    /** database **/
    runtimeOnly("mysql:mysql-connector-java")

    /** apache http component **/
    implementation("org.apache.httpcomponents:httpclient:4.5.13")

    /** jsoup **/
    implementation("org.jsoup:jsoup:1.14.3")

    /** log **/
    implementation("io.github.microutils:kotlin-logging:1.12.5")

    /** test **/
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.batch:spring-batch-test")
    testImplementation("com.h2database:h2")
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
