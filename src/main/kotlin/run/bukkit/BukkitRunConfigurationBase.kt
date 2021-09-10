/*
 * Minecraft Dev for IntelliJ
 *
 * https://minecraftdev.org
 *
 * Copyright (c) 2021 minecraft-dev
 *
 * MIT License
 */

package com.demonwav.mcdev.run.bukkit

import com.intellij.execution.Executor
import com.intellij.execution.configurations.CommandLineState
import com.intellij.execution.configurations.ConfigurationFactory
import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.execution.configurations.RunConfigurationBase
import com.intellij.execution.configurations.RunProfileState
import com.intellij.execution.process.ProcessHandler
import com.intellij.execution.process.ProcessHandlerFactory
import com.intellij.execution.process.ProcessTerminatedListener
import com.intellij.execution.runners.ExecutionEnvironment
import com.intellij.openapi.project.Project

/**
 * This class is the center of actual running.
 * The method {@link #getState()} provides the way to run the project.
 * The property's needed to run the project, such as the Minecraft Version,
 * are stored in {@link BukkitRunConfigurationOptions}.
 * Access them by {@link #super.getOptions}
 * @see BukkitRunConfigurationOptions
 * @see getOptions
 */

class BukkitRunConfigurationBase(
    project: Project,
    factory: ConfigurationFactory,
) : RunConfigurationBase<BukkitRunConfigurationOptions>(project, factory, null) {

    override fun getState(executor: Executor, environment: ExecutionEnvironment): RunProfileState {
        return object : CommandLineState(environment) {
            override fun startProcess(): ProcessHandler {
                val commandLine = GeneralCommandLine("Something here", "What ist that?")
                val processHandler = ProcessHandlerFactory.getInstance().createColoredProcessHandler(commandLine)
                ProcessTerminatedListener.attach(processHandler)
                return processHandler
                // This is the startProcces method used in the tutorial
                // TODO: Implement all steps to actually run the project.
            }
        }
    }

    override fun getConfigurationEditor() = BukkitSettingsEditor(this)
}
