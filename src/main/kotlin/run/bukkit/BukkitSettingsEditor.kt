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

import com.intellij.openapi.options.SettingsEditor
import javax.swing.JComboBox
import javax.swing.JPanel
import javax.swing.JTextField

class BukkitSettingsEditor(val configuration: BukkitRunConfigurationBase) :
    SettingsEditor<BukkitRunConfigurationBase>() {
    private lateinit var panel: JPanel
    private lateinit var runOptionsField: JTextField
    private lateinit var versionComboBox: JComboBox<String>

    override fun resetEditorFrom(bukkitRunConfigurationBase: BukkitRunConfigurationBase) {
        // TODO: Reset run configuration aka. set values in Configuration to base default
    }

    override fun applyEditorTo(bukkitRunConfigurationBase: BukkitRunConfigurationBase) {
        // TODO: Apply values from form to base
    }

    override fun createEditor() = panel

    private fun createUIComponents() {
    }
}
