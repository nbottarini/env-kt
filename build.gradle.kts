import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.10"
    id("maven-publish")
    id("signing")
    id("io.github.gradle-nexus.publish-plugin") version "1.1.0"
}

val nexusUsername: String? by project
val nexusPassword: String? by project

group = "com.nbottarini"
version = "1.0.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.github.cdimascio:dotenv-kotlin:6.2.2")
}

tasks.withType<KotlinCompile> { kotlinOptions.jvmTarget = "1.8" }

kotlin {
    sourceSets["main"].apply {
        kotlin.srcDirs("src")
        resources.srcDirs("resources")
    }
    sourceSets["test"].apply {
        kotlin.srcDir("test")
        resources.srcDir("test_resources")
    }
}

java {
    withJavadocJar()
    withSourcesJar()
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8

    sourceSets["main"].apply {
        java.srcDirs("src")
        resources.srcDirs("resources")
    }
    sourceSets["test"].apply {
        java.srcDir("test")
        resources.srcDir("test_resources")
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            artifactId = "asimov-environment"
            from(components["java"])

            pom {
                name.set("Environment")
                description.set("Tiny library to ease the use of environment variables with support for .env files")
                url.set("https://github.com/nbottarini/asimov-environment-kt")

                licenses {
                    license {
                        name.set("MIT License")
                        url.set("http://www.opensource.org/licenses/mit-license.php")
                    }
                }

                developers {
                    developer {
                        id.set("nbottarini")
                        name.set("Nicolas Bottarini")
                        email.set("nicolasbottarini@gmail.com")
                    }
                }

                scm {
                    connection.set("scm:git:git://github.com/asimov-environment-kt.git")
                    developerConnection.set("scm:git:ssh://github.com/asimov-environment-kt.git")
                    url.set("https://github.com/nbottarini/asimov-environment-kt")
                }
            }
        }
    }
}

nexusPublishing {
    repositories {
        sonatype {
            nexusUrl.set(uri("https://s01.oss.sonatype.org/service/local/"))
            snapshotRepositoryUrl.set(uri("https://s01.oss.sonatype.org/content/repositories/snapshots/"))
            username.set(nexusUsername)
            password.set(nexusPassword)
        }
    }
}

signing {
    sign(publishing.publications["maven"])
}
