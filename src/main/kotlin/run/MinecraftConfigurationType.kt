/*
 * Minecraft Dev for IntelliJ
 *
 * https://minecraftdev.org
 *
 * Copyright (c) 2021 minecraft-dev
 *
 * MIT License
 */

package com.demonwav.mcdev.run

import com.demonwav.mcdev.asset.PlatformAssets
import com.demonwav.mcdev.run.bukkit.BukkitConfigurationFactory
import com.demonwav.mcdev.run.bukkit.PaperConfigurationFactory
import com.demonwav.mcdev.run.bukkit.SpigotConfigurationFactory
import com.intellij.execution.configurations.ConfigurationFactory
import com.intellij.execution.configurations.ConfigurationType

/**
 * This class is the base of all running.
 * It provides all basic information shown to the user, such as
 * - Name
 * - Description
 * - Icon
 * - ID
 *
 * It also provides ConfigurationFactories. These factories are subtypes of the configurationType.
 * @see BukkitConfigurationFactory
 * @see SpigotConfigurationFactory
 * @see PaperConfigurationFactory
 */

class MinecraftConfigurationType : ConfigurationType {
    override fun getDisplayName() = "Minecraft"
    override fun getConfigurationTypeDescription(): String = "Allows you to run bukkit, spigot and paper projects."
    override fun getIcon() = PlatformAssets.MINECRAFT_ICON
    override fun getId(): String = "MINECRAFT_RUN"
    override fun getConfigurationFactories(): Array<ConfigurationFactory> {
        return arrayOf(
            BukkitConfigurationFactory(this),
            SpigotConfigurationFactory(this),
            PaperConfigurationFactory(this),
        )
    }

    companion object {
        const val ID = "MINECRAFT_RUN_CONFIGURATION"
    }
}
