package jetbrains.buildServer.cmd

import jetbrains.buildServer.RunBuildException
import jetbrains.buildServer.agent.*
import jetbrains.buildServer.agent.runner.Workflow
import jetbrains.buildServer.agent.runner.WorkflowComposer
import jetbrains.buildServer.agent.runner.WorkflowContext
import jetbrains.buildServer.util.OSType
import java.io.File

class CmdWorkflowComposer(
        private val _argumentsService: ArgumentsService,
        private val _environment: Environment,
        private val _virtualContext: VirtualContext)
    : WorkflowComposer {

    override val target: TargetType = TargetType.Host

    override fun compose(context: WorkflowContext, workflow: Workflow) =
            when (_environment.os) {
                OSType.WINDOWS -> {
                    Workflow(sequence {
                        val cmdExecutable = _environment.tryGetVariable(ComSpecEnvVarName) ?: throw RunBuildException("Environment variable \"$ComSpecEnvVarName\" was not found")
                        for (commandLine in workflow.commandLines) {
                            when (commandLine.executableFile.extension().toLowerCase()) {
                                "cmd", "bat" -> {
                                    yield(CommandLine(
                                            TargetType.Host,
                                            Path(_virtualContext.resolvePath(cmdExecutable)),
                                            Path(_virtualContext.resolvePath(commandLine.workingDirectory.path)),
                                            getArguments(commandLine).toList(),
                                            commandLine.environmentVariables))
                                }
                                else -> yield(commandLine)
                            }
                        }
                    })
                }
                else -> workflow
            }

    private fun getArguments(commandLine: CommandLine) = sequence {
        yield(CommandLineArgument("/D"))
        yield(CommandLineArgument("/C"))
        val args = sequenceOf(commandLine.executableFile.path).plus(commandLine.arguments.map { it.value }).map { _virtualContext.resolvePath(it) }
        yield(CommandLineArgument("\"${_argumentsService.combine(args)}\"", CommandLineArgumentType.Mandatory))
    }

    companion object {
        internal const val ComSpecEnvVarName = "ComSpec"
    }
}