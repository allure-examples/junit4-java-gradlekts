plugins {
    java
}

tasks.withType(Wrapper::class) {
    gradleVersion = "8.10.2"
}

group = "com.example.junit4"
version = "1.0-SNAPSHOT"

val allureVersion = "2.29.0"
val aspectJVersion = "1.9.22"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

tasks.withType(JavaCompile::class) {
    options.encoding = "UTF-8"
    options.compilerArgs.add("-parameters")
}

val agent: Configuration by configurations.creating {
    isCanBeConsumed = true
    isCanBeResolved = true
}

tasks.test {
    ignoreFailures = true
    useJUnit()
    jvmArgs = listOf(
        "-javaagent:${agent.singleFile}"
    )
}

dependencies {
    agent("org.aspectj:aspectjweaver:$aspectJVersion")
    
    testImplementation(platform("io.qameta.allure:allure-bom:$allureVersion"))
    testImplementation("io.qameta.allure:allure-junit4")
    testImplementation("io.qameta.allure:allure-junit4-aspect")

    testImplementation("junit:junit:4.13.2")

    testImplementation("org.slf4j:slf4j-simple:2.0.16")
}

repositories {
    mavenCentral()
}
