plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization") version "1.3.70"

}


kotlin {
    val ktorVersion = "1.3.0"

    val kotlinxSerializationVersion = "0.12.0"

    jvm("android")

    sourceSets["commonMain"].dependencies {
        implementation("io.ktor:ktor-client-core:$ktorVersion")
        implementation("io.ktor:ktor-client-json:$ktorVersion")
        implementation("io.ktor:ktor-client-serialization:$ktorVersion")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-common")
        implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime:$kotlinxSerializationVersion")
        implementation("io.ktor:ktor-client-logging:$ktorVersion")
        implementation("org.slf4j:slf4j-simple:1.7.26")
        implementation("io.ktor:ktor-client-logging-jvm:$ktorVersion")
    }

    sourceSets["androidMain"].dependencies {
        implementation("org.jetbrains.kotlin:kotlin-stdlib")
        implementation("io.ktor:ktor-client-json-jvm:$ktorVersion")
        implementation("io.ktor:ktor-client-gson:$ktorVersion")
        implementation("io.ktor:ktor-client-android:$ktorVersion")
        implementation("io.ktor:ktor-client-logging-jvm:$ktorVersion")
        implementation("io.ktor:ktor-client-serialization-jvm:$ktorVersion")
        implementation("org.slf4j:slf4j-simple:1.7.26")
        implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime:$kotlinxSerializationVersion")
    }
}
