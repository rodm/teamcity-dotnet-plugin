
plugins {
    id ("base")
}

repositories {
    ivy {
        url = uri("https://globalcdn.nuget.org/packages/")
        patternLayout {
            artifact("[artifact].[revision].nupkg")
        }
        metadataSources {
            artifact()
        }

    }
}

configurations.register("dotnet")
