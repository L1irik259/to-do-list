plugins {
    id ("org.springframework.boot") version ("3.2.5") // Вместо 2.x.x
    id ("io.spring.dependency-management") version ("1.1.4")
    application
    java
}

repositories {
    mavenCentral()
}

val springBootVersion = "3.2.5"

dependencies {
    // Spring Boot
    implementation("org.springframework.boot:spring-boot-starter:$springBootVersion")
    implementation("org.springframework.boot:spring-boot-starter-web:$springBootVersion")

    // Telegram Bot
    implementation("org.telegram:telegrambots-spring-boot-starter:6.8.0")

    // PostgreSQL и Hibernate
    implementation("org.postgresql:postgresql:42.7.5")
    implementation("jakarta.persistence:jakarta.persistence-api:3.1.0")
    implementation("jakarta.transaction:jakarta.transaction-api:2.0.1")
    implementation("org.hibernate.orm:hibernate-core:6.2.0.Final")

    // Логирование
    implementation("org.slf4j:slf4j-api:2.0.7")
    implementation("ch.qos.logback:logback-classic:1.4.7")

    // Прочее
    implementation("com.google.guava:guava:32.1.2-jre")

    // Тесты
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")
    testImplementation("junit:junit:4.13.2")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

application {
    mainClass.set("org.example.App")
}

tasks.test {
    useJUnitPlatform()
}
