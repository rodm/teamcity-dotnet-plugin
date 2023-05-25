/*
 * Copyright 2000-2023 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

rootProject.name = "teamcity-dotnet-plugin"

dependencyResolutionManagement {
    versionCatalogs {
        register("libs") {
            library ("google.gson","com.google.code.gson:gson:2.9.1")
            library ("commons.io", "commons-io:commons-io:2.11.0")

            library ("testng", "org.testng:testng:7.5")
            library ("mockk", "io.mockk:mockk:1.13.2")
            library ("hamcrest.all","org.hamcrest:hamcrest-all:1.3")

            version ("jmock", "2.12.0")
            library ("jmock.core", "org.jmock","jmock").versionRef("jmock")
            library ("jmock.junit4", "org.jmock", "jmock-junit4").versionRef("jmock")

            version ("kotlinx", "1.6.4")
            library ("kotlinx.core","org.jetbrains.kotlinx", "kotlinx-coroutines-core").versionRef("kotlinx")
            library ("kotlinx.test","org.jetbrains.kotlinx", "kotlinx-coroutines-test").versionRef("kotlinx")
        }
    }
}

includeBuild ("build-logic")

include ("plugin-dotnet-agent")
include ("plugin-dotnet-agent-tool")
include ("plugin-dotnet-common")
include ("plugin-dotnet-server")
include ("plugin-dotnet-server-tool")
