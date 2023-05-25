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
  id ("teamcity.agent-plugin")
}

configurations.register("tool")

dependencies {
  implementation (project(":plugin-dotnet-common"))
  implementation (kotlin("stdlib"))
  implementation (libs.kotlinx.core)
  implementation (libs.commons.io)

  provided ("org.jetbrains.teamcity.internal:agent:${teamcity.version}")

  "tool" (project(":plugin-dotnet-agent-tool"))

  testImplementation (kotlin("reflect"))
  testImplementation (libs.testng)
  testImplementation (libs.jmock.core)
  testImplementation (libs.hamcrest.all)
  testImplementation (libs.mockk)
  testImplementation (libs.kotlinx.test)
}

teamcity {
  agent {
    archiveName = "dotnet"
    descriptor = project.file("teamcity-plugin.xml")
    files {
      into("tools") {
        from (configurations["tool"])
      }
    }
  }
}
