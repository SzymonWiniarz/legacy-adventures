import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.11"
}

allprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")

    group = "com.simcode.legacy-adventures"
    version = "0.1.0"

    repositories {
        mavenCentral()
    }

    dependencies {
        implementation(kotlin("stdlib-jdk8"))

        testImplementation("org.junit.jupiter:junit-jupiter-api:5.3.1")
        testImplementation("org.junit.jupiter:junit-jupiter-engine:5.3.1")
        testImplementation("org.assertj:assertj-core:3.12.0")
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }

    tasks.withType<Wrapper> {
        gradleVersion = "4.10"
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}

dependencies {
    implementation(project(":adventures"))
    implementation(project(":game"))
    implementation(project(":ui-cli"))
}