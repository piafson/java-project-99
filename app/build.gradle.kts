import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
	application
	jacoco
	id("checkstyle")
	id("io.freefair.lombok") version "8.4"
	id("org.springframework.boot") version "3.2.1"
	id("io.spring.dependency-management") version "1.1.4"
	id("io.sentry.jvm.gradle") version "4.2.0"
}

group = "hexlet.code"
version = "0.0.1-SNAPSHOT"


repositories {
	mavenCentral()
}

buildscript {
	repositories {
		mavenCentral()
	}
}

sentry {
	includeSourceContext = true

	org = "piafson"
	projectName = "java"
	authToken = System.getenv("SENTRY_AUTH_TOKEN")
}

application {
	mainClass.set("hexlet.code.AppApplication")
}

dependencies {
	implementation("org.mapstruct:mapstruct:1.5.5.Final")
	annotationProcessor("org.mapstruct:mapstruct-processor:1.5.5.Final")
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-devtools")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
	implementation("net.datafaker:datafaker:2.0.2")
	implementation("org.instancio:instancio-junit:3.3.1")
	implementation("org.openapitools:jackson-databind-nullable:0.2.6")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0")
	runtimeOnly("com.h2database:h2")
	runtimeOnly("org.postgresql:postgresql:42.7.0")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation(platform("org.junit:junit-bom:5.10.0"))
	testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")
	testImplementation("net.javacrumbs.json-unit:json-unit-assertj:3.2.2")
	testImplementation("org.springframework.security:spring-security-test")

}

tasks.test {
	useJUnitPlatform()
	testLogging {
		exceptionFormat = TestExceptionFormat.FULL
		events = mutableSetOf(TestLogEvent.FAILED, TestLogEvent.PASSED, TestLogEvent.SKIPPED)
		showStandardStreams = true
	}
}

tasks.jacocoTestReport {
    reports {
        xml.required.set(true)
    }
}


