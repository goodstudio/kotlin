plugins {
    kotlin("jvm")
    id("jps-compatible")
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    testImplementation(project(":analysis:analysis-api"))
    testImplementation(project(":analysis:low-level-api-fir"))
    testImplementation("org.jetbrains.kotlinx:lincheck:2.23")
}

sourceSets {
    "test" { projectDefault() }
}

kotlin {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }
}

projectTest(jUnitMode = JUnitMode.JUnit5) {
    dependsOn(":dist")
    workingDir = rootDir
    useJUnitPlatform()
}
