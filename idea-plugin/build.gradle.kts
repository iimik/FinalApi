plugins {
    id("org.jetbrains.intellij") version "1.17.1"
}

group = "com.itangcent"
version = properties["plugin_version"]!!

val intellijVersions = arrayOf(
    mapOf("jdk" to 17, "version" to "2023.1.3", "since" to "231"),
    mapOf("jdk" to 15, "version" to "2022.2.3", "since" to "223"),
    mapOf("jdk" to 11, "version" to "2021.2.1", "since" to "212")
)

val javaVersion = JavaVersion.current().majorVersion.toInt()
val (intellijVersion, intellijSince) = intellijVersions.first { javaVersion >= (it["jdk"] as Int) }.let {
    it["version"].toString() to it["since"].toString()
}
println("use intellij $intellijVersion")

repositories {
    mavenCentral()
}

dependencies {

    compileOnly("org.projectlombok:lombok:1.18.36")
    annotationProcessor("org.projectlombok:lombok:1.18.36")
    testCompileOnly("org.projectlombok:lombok:1.18.36")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.36")

    // https://mvnrepository.com/artifact/org.eclipse.jgit/org.eclipse.jgit
    implementation("org.eclipse.jgit:org.eclipse.jgit:7.1.0.202411261347-r")
    // spring boot
    implementation("org.springframework.boot:spring-boot-starter:${properties["spring.boot.version"]}")
    implementation("org.springframework.boot:spring-boot-starter-aop:${properties["spring.boot.version"]}")
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign:4.2.0")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.9.8")
    testImplementation("org.springframework.boot:spring-boot-starter-web:${properties["spring.boot.version"]}")
    testImplementation("org.springframework.boot:spring-boot-starter-test:${properties["spring.boot.version"]}")

    implementation(project(":common-api")) {
        exclude("org.apache.httpcomponents", "httpclient")
    }
    implementation(project(":plugin-adapter:plugin-adapter-markdown"))


    implementation("com.itangcent:commons:${properties["itangcent_intellij_version"]}") {
        exclude("com.google.inject")
        exclude("com.google.code.gson")
    }


    implementation("com.itangcent:guice-action:${properties["itangcent_intellij_version"]}") {
        exclude("com.google.inject")
        exclude("com.google.code.gson")
    }

    implementation("com.itangcent:intellij-jvm:${properties["itangcent_intellij_version"]}") {
        exclude("com.google.inject")
        exclude("com.google.code.gson")
    }

    implementation("com.itangcent:intellij-idea:${properties["itangcent_intellij_version"]}") {
        exclude("com.google.inject")
        exclude("com.google.code.gson")
    }

    implementation("com.itangcent:intellij-kotlin-support:${properties["itangcent_intellij_version"]}") {
        exclude("com.google.inject")
        exclude("com.google.code.gson")
    }

    implementation("com.itangcent:intellij-groovy-support:${properties["itangcent_intellij_version"]}") {
        exclude("com.google.inject")
        exclude("com.google.code.gson")
    }

//    implementation("com.itangcent:intellij-scala-support:${properties["itangcent_intellij_version"]}") {
//        exclude("com.google.inject")
//        exclude("com.google.code.gson")
//    }

    implementation("com.google.inject:guice:4.2.2") {
        exclude("org.checkerframework", "checker-compat-qual")
        exclude("com.google.guava", "guava")
    }

    implementation("org.jetbrains.kotlin:kotlin-reflect:1.8.0")

    // https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind
    implementation("com.fasterxml.jackson.core:jackson-databind:2.12.2")

    // https://mvnrepository.com/artifact/org.xerial/sqlite-jdbc
    implementation("org.xerial:sqlite-jdbc:3.34.0")

    // https://mvnrepository.com/artifact/com.squareup.okhttp3/okhttp
    implementation("com.squareup.okhttp3:okhttp:4.12.0")


    // https://search.maven.org/artifact/org.mockito.kotlin/mockito-kotlin/3.2.0/jar
    testImplementation("org.mockito.kotlin:mockito-kotlin:3.2.0")

    // https://mvnrepository.com/artifact/org.mockito/mockito-inline
    testImplementation("org.mockito:mockito-inline:3.11.0")

    testImplementation("com.itangcent:intellij-idea-test:${properties["itangcent_intellij_version"]}")

    testImplementation("org.jetbrains.kotlin:kotlin-test")

    testImplementation("org.junit.jupiter:junit-jupiter-params:${properties["junit_version"]}")
    testImplementation("org.junit.jupiter:junit-jupiter-api:${properties["junit_version"]}")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${properties["junit_version"]}")
    testRuntimeOnly("org.junit.vintage:junit-vintage-engine:5.7.1")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5:1.8.0")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

intellij {
    version.set(intellijVersion)
    type.set("IC")
    pluginName.set("final-aio")
    sandboxDir.set("idea-sandbox")
    plugins.set(listOf("java", "maven", "gradle", "Kotlin"))
}

tasks {
    patchPluginXml {
        pluginDescription.set(file("parts/pluginDescription.html").readText())
        changeNotes.set(file("parts/pluginChanges.html").readText())

        sinceBuild.set(intellijSince)
        untilBuild.set("")
    }

    runIde {
        jvmArgs("-Xmx4096m")
    }
}
