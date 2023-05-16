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
  id ("teamcity.dotnet-tool")
}

dependencies {
  dotnet (":jetbrains.resharper.commandlinetools:2021.1.2")
}

tasks {
  register<Copy>("unpackNuspec") {
    destinationDir = layout.buildDirectory.dir("nuspec").get().asFile
    into("") {
      from(zipTree(configurations.dotnet.get().singleFile)) {
        include("*.nuspec")
      }
    }
  }

  register<Zip>("resharperTool") {
    destinationDirectory.set(layout.buildDirectory.dir("tool"))
    archiveFileName.set("jetbrains.resharper-clt.bundled.zip")
    from(zipTree(configurations.dotnet.get().singleFile)) {
      includeEmptyDirs = false
      include("tools/**")
    }
    into("") {
      from("bundled-tool.xml")
      rename("bundled-tool.xml", "teamcity-plugin.xml")
    }
  }
}

artifacts {
  add("default", tasks.named("unpackNuspec"))
  add("default", tasks.named("resharperTool"))
  add("default", file("bundled-tool.xml"))
}
