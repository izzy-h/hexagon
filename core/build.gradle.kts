
plugins {
    id("java-library")
}

apply(from = "../gradle/kotlin.gradle")
apply(from = "../gradle/publish.gradle")
apply(from = "../gradle/dokka.gradle")
apply(from = "../gradle/detekt.gradle")

description = "Hexagon core utilities. Includes serialization and logging helpers."

dependencies {
    "api"("org.jetbrains.kotlin:kotlin-stdlib")

    "testImplementation"("org.jetbrains.kotlin:kotlin-reflect")
}
