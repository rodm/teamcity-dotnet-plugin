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

import java.text.SimpleDateFormat
import java.util.Date

plugins {
    id ("org.jetbrains.kotlin.jvm")
    id ("io.github.rodm.teamcity-base")
}

repositories {
    mavenCentral()
}

group = "teamcity-dotnet-plugin"

val defaultVersion = "SNAPSHOT_${SimpleDateFormat("yyyyMMddHHmmss").format(Date())}"
val versionNumber = project.findProperty("versionNumber") ?: ""
val validVersion = Regex("\\d+(\\.\\d+\\.\\d+.*)?").matches(versionNumber.toString())
version = if (validVersion) versionNumber else defaultVersion

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

teamcity {
    version = (project.findProperty("teamcityVersion") ?: "2022.12-SNAPSHOT") as String
    allowSnapshotVersions = true
}

tasks {
    test {
        useTestNG()
    }
    jar {
        version = null
    }
}
