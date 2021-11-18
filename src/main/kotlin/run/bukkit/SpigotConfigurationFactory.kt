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
import com.demonwav.mcdev.platform.PlatformType
import com.demonwav.mcdev.run.MinecraftConfigurationType
import com.intellij.execution.configurations.ConfigurationFactory
import com.intellij.execution.configurations.ConfigurationType
import com.intellij.execution.configurations.RunConfiguration
import com.intellij.openapi.components.BaseState
import com.intellij.openapi.project.Project

/**
 * The purpose of this is providing a template of a configuration.
 * This method is called once per project to create the template run configuration.
 * @see createTemplateConfiguration
 */

class SpigotConfigurationFactory(type: ConfigurationType) : ConfigurationFactory(type) {
    override fun getId() = MinecraftConfigurationType.ID
    override fun getName() = "Spigot"
    override fun getIcon() = PlatformAssets.SPIGOT_ICON

    override fun createTemplateConfiguration(project: Project): RunConfiguration = BukkitRunConfiguration(
        project,
        this,
        PlatformType.SPIGOT
    )

    override fun getOptionsClass(): Class<out BaseState> {
        return BukkitRunConfigurationOptions::class.java
    }
}
