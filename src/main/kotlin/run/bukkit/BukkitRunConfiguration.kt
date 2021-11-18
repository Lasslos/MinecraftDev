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

import com.demonwav.mcdev.asset.PlatformAssets
import com.demonwav.mcdev.creator.getVersionSelector
import com.demonwav.mcdev.platform.PlatformType
import com.intellij.execution.Executor
import com.intellij.execution.configurations.ConfigurationFactory
import com.intellij.execution.configurations.JavaCommandLineState
import com.intellij.execution.configurations.JavaParameters
import com.intellij.execution.configurations.RunConfiguration
import com.intellij.execution.configurations.RunProfileState
import com.intellij.execution.configurations.RuntimeConfigurationException
import com.intellij.execution.runners.ExecutionEnvironment
import com.intellij.openapi.project.Project

/**
 * This class is the center of actual running.
 * The method {@link #getState()} provides the way to run the project.
 * The property's needed to run the project, such as the Minecraft Version,
 * are stored in {@link BukkitRunConfigurationOptions}.
 * Access them by {@link #super.getOptions}
 * @see BukkitRunConfigurationOptions
 */

class BukkitRunConfiguration(
    private val _project: Project,
    private val _factory: ConfigurationFactory,
    val platformType: PlatformType,
    private var _name: String? = null,
) : RunConfiguration {
    override fun getConfigurationEditor() = BukkitSettingsEditor(this)
    override fun getProject(): Project = _project
    override fun setName(name: String?) {
        this._name = name
    }
    override fun getName() = this._name ?: "${platformType.name} $minecraftVersion"
    override fun getIcon() = when (platformType) {
        PlatformType.BUKKIT -> PlatformAssets.BUKKIT_ICON
        PlatformType.SPIGOT -> PlatformAssets.SPIGOT_ICON
        PlatformType.PAPER -> PlatformAssets.PAPER_ICON
        else -> PlatformAssets.MINECRAFT_ICON
    }
    override fun getFactory(): ConfigurationFactory = _factory
    override fun clone(): RunConfiguration {
        val result = BukkitRunConfiguration(
            _project,
            _factory,
            platformType,
            _name,
        )
        result.options.runOptions = this.runOptions
        result.options.minecraftVersion = this.minecraftVersion
        return result
    }

    val options = BukkitRunConfigurationOptions()

    var runOptions: String?
        get() = options.runOptions
        set(value) {
            options.runOptions = value
        }

    var minecraftVersion: String?
        get() = options.minecraftVersion
        set(value) {
            options.minecraftVersion = value
        }

    override fun getState(executor: Executor, environment: ExecutionEnvironment): RunProfileState {
        val result = object : JavaCommandLineState(environment) {
            override fun createJavaParameters(): JavaParameters {
                // TODO: Implement actually running the steps.
                return JavaParameters()
            }
        }
        return result
    }

    override fun checkConfiguration() {
        val versionsList = getVersionSelector(platformType).versions
        if (!versionsList.contains(minecraftVersion)) {
            throw RuntimeConfigurationException(
                "The Minecraft Version $minecraftVersion could not be found." +
                    "Try selecting a different one."
            )
        }
    }
}
