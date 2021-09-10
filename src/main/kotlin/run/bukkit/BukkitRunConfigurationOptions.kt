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

import com.intellij.execution.configurations.RunConfigurationOptions

/**
 * All options, such as Minecraft Version, are stored here.
 * They can be accessed by {@link BukkitRunConfigurationBase}.
 */

class BukkitRunConfigurationOptions : RunConfigurationOptions() {
    // TODO: Store minecraft version, run args and if bukkit, spigot or paper should be used.
    //  It is also possible to get that from the plugin.yml, that would be tough, though.
}
