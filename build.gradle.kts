plugins {
    `java-platform`
    `maven-publish`
    `version-catalog`
    signing
}

group = "de.c-otto"
version = versioncatalog.versions.platform.get()

javaPlatform {
    allowDependencies()
}

dependencies {
    api(platform(versioncatalog.testing.assertJBom))

    constraints {
        api(versioncatalog.gradleplugins.testLogger)
        api(versioncatalog.gradleplugins.spotbugs)
        api(versioncatalog.gradleplugins.cpd)
        api(versioncatalog.gradleplugins.pitest)
        api(versioncatalog.gradleplugins.errorprone)
        api(versioncatalog.gradleplugins.nullaway)

        api(versioncatalog.gradleplugins.library.findbugsJsr305)
        api(versioncatalog.gradleplugins.library.errorprone)
        api(versioncatalog.gradleplugins.library.nullaway)

        api(versioncatalog.caffeine)
        api(versioncatalog.guava)
        api(versioncatalog.metricsSpring)
        api(versioncatalog.commonsCodec)
        api(versioncatalog.resilience4jSpringBoot2)
        api(versioncatalog.resilience4jSpringBoot3)
        api(versioncatalog.javaxAnnotationApi)
        api(versioncatalog.commonsLang)
        api(versioncatalog.eclipseCollections)
        api(versioncatalog.ini4j)
        api(versioncatalog.bootstrap)
        api(versioncatalog.webjarsLocator)
        api(versioncatalog.logbackClassic)
        api(versioncatalog.slf4jApi)

        api(versioncatalog.testing.slf4jTest)
        api(versioncatalog.testing.archunit)
        api(versioncatalog.testing.equalsverifier)
        api(versioncatalog.testing.awaitility)
    }
}

catalog {
    versionCatalog {
        from(files("gradle/versioncatalog.toml"))
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            artifactId = "version-catalog"
            from(components["versionCatalog"])

            pom {
                name.set("Version Catalog")
                description.set("This provides some versions for plugins and libraries I often use")
                url.set("https://github.com/C-Otto/java-platform")
                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
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
        create<MavenPublication>("java-platform") {
            from(components["javaPlatform"])

            pom {
                name.set("Java Platform")
                description.set("This provides some versions for plugins and libraries I often use")
                url.set("https://github.com/C-Otto/java-platform")
                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
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
