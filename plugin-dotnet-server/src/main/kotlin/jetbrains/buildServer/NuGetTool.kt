/*
 * Copyright 2000-2021 JetBrains s.r.o.
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

package jetbrains.buildServer

import jetbrains.buildServer.dotnet.DotnetConstants
import jetbrains.buildServer.tools.ToolType
import jetbrains.buildServer.tools.ToolVersion

class NuGetTool(
        private val _toolType: ToolType,
        private val _package: NuGetPackage) : ToolVersion {

    val downloadUrl get() = _package.downloadUrl.toString()

    val destinationFileName get() = "${_package.packageId}.${_package.packageVersion}.${DotnetConstants.PACKAGE_NUGET_EXTENSION}"

    override fun getType() = _toolType

    override fun getVersion() =_package.packageVersion.toString()

    override fun getId() = _toolType.type + "." + _package.packageVersion

    override fun getDisplayName() = _toolType.type + version
}