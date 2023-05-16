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
  implementation ("org.jetbrains.kotlin:kotlin-stdlib:1.7.20")
  implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
  provided ("org.jetbrains.teamcity.internal:agent:${teamcity.version}")
  implementation ("commons-io:commons-io:2.11.0")
  "tool"(project(":plugin-dotnet-agent-tool"))

  testImplementation ("org.testng:testng:7.5")
  testImplementation ("org.jmock:jmock:2.12.0")
  testImplementation ("org.hamcrest:hamcrest-all:1.3")
  testImplementation ("org.jetbrains.kotlin:kotlin-reflect")
  testImplementation ("io.mockk:mockk:1.13.2")
  testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")
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
