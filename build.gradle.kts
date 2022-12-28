plugins {
    `java-platform`
    `maven-publish`
    signing
}

group = "de.c-otto"
version = "2022.12.28_2"

javaPlatform {
    allowDependencies()
}

dependencies {
    constraints {
        // Gradle plugins
        api("com.adarshr:gradle-test-logger-plugin:3.2.0")
        api("com.github.spotbugs.snom:spotbugs-gradle-plugin:5.0.13")
        api("de.aaschmid:gradle-cpd-plugin:3.3")
        api("info.solidsoft.gradle.pitest:gradle-pitest-plugin:1.9.11")
        api("net.ltgt.gradle:gradle-errorprone-plugin:3.0.1")
        api("net.ltgt.gradle:gradle-nullaway-plugin:1.5.0")
        api("com.vanniktech.maven.publish.base:0.22.0")

        // Libraries for plugins
        api("com.google.code.findbugs:jsr305:3.0.2")
        api("com.google.errorprone:error_prone_core:2.16")
        api("com.uber.nullaway:nullaway:0.10.5")

        // Libraries
        api("com.github.ben-manes.caffeine:caffeine:3.1.2")
        api("com.google.guava:guava:31.1-jre")
        api("com.ryantenney.metrics:metrics-spring:3.1.3")
        api("commons-codec:commons-codec:1.15")
        api("io.github.resilience4j:resilience4j-spring-boot2:2.0.2")
        api("javax.annotation:javax.annotation-api:1.3.2")
        api("org.apache.commons:commons-lang3:3.12.0")
        api("org.eclipse.collections:eclipse-collections:11.1.0")
        api("org.ini4j:ini4j:0.5.4")
        api("org.webjars:bootstrap:5.2.3")
        api("org.webjars:webjars-locator:0.46")

        // Testing Libraries
        api("com.github.valfirst:slf4j-test:2.6.1")
        api("com.tngtech.archunit:archunit:1.0.1")
        api("nl.jqno.equalsverifier:equalsverifier:3.12.2")
        api("org.awaitility:awaitility:4.2.0")
    }
}

publishing {
    publications {
        create<MavenPublication>("java-platform") {
            from(components["javaPlatform"])

            pom {
                name.set("Java Platform")
                description.set("This provides some versions for libraries I often use")
                url.set("https://github.com/C-Otto/java-platform")
                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                developers {
                    developer {
                        id.set("cotto")
                        name.set("Carsten Otto")
                        email.set("git@c-otto.de")
                    }
                }
                scm {
                    connection.set("scm:git:git://github.com/C-Otto/java-platform.git")
                    developerConnection.set("scm:git:ssh://github.com/C-Otto/java-platform.git")
                    url.set("https://github.com/C-Otto/java-platform")
                }
            }
            repositories {
                maven {
                    name = "OSSRH"
                    setUrl("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2")
                    credentials {
                        username = "C-Otto"
                        password = System.getenv("OSSRH_PASSWORD") ?: return@credentials
                    }
                }
            }
        }
    }
}

configure<SigningExtension> {
    isRequired = true

    val publishing: PublishingExtension by project

    useGpgCmd()
    sign(publishing.publications)
}
