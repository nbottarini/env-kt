[![Maven](https://img.shields.io/maven-central/v/com.nbottarini/asimov-environment.svg)](https://search.maven.org/#search%7Cgav%7C1%7Cg%3A%22com.nbottarini%22%20AND%20a%3A%22asimov-environment%22)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![CI Status](https://github.com/nbottarini/asimov-environment-kt/actions/workflows/gradle.yml/badge.svg?branch=main)](https://github.com/nbottarini/asimov-environment-kt/actions?query=branch%3Amain+workflow%3Aci)

# asimov/environment
Tiny library to ease the use of environment variables with support for .env files.

## Installation

#### Gradle (Kotlin)

```kotlin
repositories {
    mavenCentral()
}

dependencies {
    implementation("com.nbottarini:asimov-environment:1.0")
}
```

#### Gradle (Groovy)

```groovy
repositories {
    mavenCentral()
}

dependencies {
    implementation 'com.nbottarini:asimov-environment:1.0'
}
```

#### Maven

```xml
<dependency>
    <groupId>com.nbottarini</groupId>
    <artifactId>asimov-environment</artifactId>
    <version>1.0</version>
</dependency>
```

## Usage

```kotlin
val myEnvValue: String? = Environment["my-env-var"]

val myEnvValue: String? = Environment.get("my-env-var")

val myEnvValue: String = Environment.get("my-env-var", "default value")

val myEnvValue: String = Environment.getOrThrow("my-env-var") // Throws IllegalArgumentException if env var is not present

val allEnvVars = Environment.getAll()
```

### .env

You can create a .env file in the project directory to set environment variables for your development environment.

Sample .env file:
```dotenv
VAR1=VALUE1
VAR2=VALUE2
```

It is recommended to ignore the .env file from git. You can commit a sample .env.dist file with the default environment 
variables (without sensitive values like passwords).

The system environment variables takes precedence over .env variables.

By default, the library tries to find the .env file in the working dir and in the parent dirs.

You can specify additional directories to search:

```kotlin
Environment.addSearchPath('./myDir')
```

Search paths must be configured before accessing any environment variable.
