import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.2.2"
    id("io.spring.dependency-management") version "1.1.4"
    kotlin("jvm") version "1.9.22"
    kotlin("plugin.spring") version "1.9.22"
}

group = "io.sch"
version = "1.3.0"

java {
    sourceCompatibility = JavaVersion.VERSION_21
}

repositories {
    mavenCentral()
    maven("https://repo1.maven.org/maven2/")
}

sourceSets {
    main {
        kotlin {
            srcDirs("src/main/kotlin")
        }
    }
    test {
        kotlin {
            srcDirs("src/test/kotlin")
        }
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-hateoas")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.eclipse.jgit:org.eclipse.jgit:6.7.0.202309050840-r")
    implementation("org.eclipse.jgit:org.eclipse.jgit.ssh.apache:6.7.0.202309050840-r")
    implementation("org.eclipse.jgit:org.eclipse.jgit.gpg.bc:6.7.0.202309050840-r")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("com.tngtech.archunit:archunit:1.2.0")
}
tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "21"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
    testLogging {
//        showStandardStreams = true
//        exceptionFormat = "full"
//        events "passed", "skipped", "failed"
    }
}
