import java.io.DataInputStream
import java.io.FileInputStream
import java.util.Properties

pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://maven.pkg.github.com/CarOS-Android/BlindTouchSamples")
            val properties = Properties()
            properties.load(DataInputStream(FileInputStream(file("local.properties"))))
            credentials {
                username = properties.getProperty("USERNAME")
                password = properties.getProperty("TOKEN")
            }
        }
    }
}

rootProject.name = "2025CarAPP"
include(":app")
 