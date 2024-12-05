plugins {
    id("java")
}

group = "com.macher259.aoc2024"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<JavaCompile> {
    options.compilerArgs.add("--enable-preview")
}

tasks.withType<JavaExec>().configureEach { jvmArgs("--enable-preview") }

gradle.startParameter.showStacktrace = ShowStacktrace.ALWAYS