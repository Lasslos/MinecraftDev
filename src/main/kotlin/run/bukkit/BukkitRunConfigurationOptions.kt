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
import com.intellij.openapi.components.StoredProperty

/**
 * All options, such as Minecraft Version, are stored here.
 * They can be accessed by {@link BukkitRunConfigurationBase}.
 */

class BukkitRunConfigurationOptions : RunConfigurationOptions() {
    private val runOptionsStored: StoredProperty<String?> = string("nogui").provideDelegate(this, "runOptions")
    private val minecraftVersionStored: StoredProperty<String?> = string(null).provideDelegate(this, "minecraftVersion")

    var runOptions: String?
        get() = runOptionsStored.getValue(this)
        set(value) {
            runOptionsStored.setValue(this, value)
        }

    var minecraftVersion: String?
        get() = minecraftVersionStored.getValue(this)
        set(value) {
            minecraftVersionStored.setValue(this, value)
        }
}
