package jetbrains.buildServer.dotnet

import jetbrains.buildServer.RunBuildException
import jetbrains.buildServer.agent.ToolCannotBeFoundException
import jetbrains.buildServer.dotnet.arguments.*
import jetbrains.buildServer.runners.*
import java.io.File
import kotlin.coroutines.experimental.buildSequence

class DotnetWorkflowComposer(
        private val _pathsService: PathsService,
        private val _defaultEnvironmentVariables: EnvironmentVariables,
        private val _dotnetArgumentsProviderSource: ArgumentsProviderSource) : WorkflowComposer {

    override val target: TargetType
        get() = TargetType.Tool

    override fun compose(context: WorkflowContext, workflow: Workflow): Workflow {
        if(workflow.commandLines.any()) {
            throw RunBuildException("This composer should be a root")
        }

        val toolPath: File
        try {
            toolPath = _pathsService.getToolPath(DotnetConstants.RUNNER_TYPE)
        }
        catch (e: ToolCannotBeFoundException) {
            val exception = RunBuildException(e)
            exception.isLogStacktrace = false
            throw exception
        }

        @Suppress("EXPERIMENTAL_FEATURE_WARNING")
        return Workflow(
                buildSequence {
                    for (argumentProvider in _dotnetArgumentsProviderSource) {
                        yield(
                                CommandLine(
                                        TargetType.Tool,
                                        toolPath,
                                        _pathsService.getPath(PathType.WorkingDirectory),
                                        argumentProvider.getArguments().toList(),
                                        _defaultEnvironmentVariables.variables.toList()))
                    }
                }
        )
    }
}