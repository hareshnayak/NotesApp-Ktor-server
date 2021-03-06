val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val exposed_version: String by project
val postgres_version: String by project
val hikari_version: String by project

plugins {
    application
    kotlin("jvm") version "1.6.10"
}

group = "com.hareshnayak"
version = "0.0.1"
application {
    mainClass.set("com.hareshnayak.ApplicationKt")
}

tasks.create("stage"){
    dependsOn("installDist")
}

repositories {
    mavenCentral()
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/ktor/eap") }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlin_version")

    implementation("io.ktor:ktor-server-core:$ktor_version")

    implementation ("io.ktor:ktor-auth:$ktor_version")
    implementation ("io.ktor:ktor-auth-jwt:$ktor_version")

//    implementation("io.ktor:ktor-server-content-negotiation:$ktor_version")
    implementation("io.ktor:ktor-gson:$ktor_version")

    implementation("io.ktor:ktor-server-sessions:$ktor_version")

    implementation("io.ktor:ktor-server-netty:$ktor_version")

    implementation("ch.qos.logback:logback-classic:$logback_version")

    implementation("io.ktor:ktor-locations:$ktor_version")

    testImplementation("io.ktor:ktor-server-tests:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")

    implementation("org.jetbrains.exposed:exposed-core:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-dao:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposed_version")
    implementation("org.postgresql:postgresql:$postgres_version")
    implementation("com.zaxxer:HikariCP:$hikari_version")
}