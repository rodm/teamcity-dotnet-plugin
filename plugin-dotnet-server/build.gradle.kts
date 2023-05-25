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

plugins {
  id ("teamcity.server-plugin")
}

configurations.register("tool")

dependencies {
  implementation (project(":plugin-dotnet-common"))
  implementation (kotlin("stdlib"))
  implementation (libs.google.gson)
  implementation (libs.kotlinx.core)

  provided ("org.jetbrains.teamcity.internal:server-tools:${teamcity.version}")

  testImplementation (kotlin("reflect"))
  testImplementation (libs.testng)
  testImplementation (libs.jmock.junit4)
  testImplementation (libs.mockk)
  testImplementation (libs.kotlinx.test)

  agent (project(path = ":plugin-dotnet-agent", configuration = "plugin"))

  "tool" (project(":plugin-dotnet-server-tool"))
}

teamcity {
  server {
    archiveName = "dotnet"
    descriptor = project.file("teamcity-plugin.xml")
    tokens = mapOf("Plugin_Version" to project.version)
    files {
      into("kotlin-dsl") {
        from ("${rootProject.projectDir}/kotlin-dsl")
      }
      into("server/bundled-tool") {
        from (configurations["tool"])
      }
    }
  }
}
