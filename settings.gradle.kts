rootProject.name = "java-platform"

dependencyResolutionManagement {
    versionCatalogs {
        create("versioncatalog") {
            from(files("gradle/versioncatalog.toml"))
        }
    }
}
