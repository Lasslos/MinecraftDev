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

import com.demonwav.mcdev.creator.getVersionSelector
import com.intellij.openapi.options.SettingsEditor
import javax.swing.JComboBox
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JTextField
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.swing.Swing
import kotlinx.coroutines.withContext

class BukkitSettingsEditor(val configuration: BukkitRunConfiguration) :
    SettingsEditor<BukkitRunConfiguration>() {
    private lateinit var panel: JPanel
    private lateinit var runOptionsField: JTextField
    private lateinit var versionComboBox: JComboBox<String>
    private lateinit var errorLabel: JLabel

    private var versionsLoaded = false

    override fun resetEditorFrom(bukkitRunConfiguration: BukkitRunConfiguration) {
        runOptionsField.text = bukkitRunConfiguration.runOptions
        versionsLoaded = false
        loadVersions()
        val items = Array<String>(versionComboBox.itemCount) {
            return@Array versionComboBox.getItemAt(it)
        }
        if (items.isEmpty()) return
        var indexOfValue = items.indexOf(bukkitRunConfiguration.minecraftVersion)
        if (indexOfValue == -1) {
            indexOfValue = 0
            bukkitRunConfiguration.minecraftVersion = versionComboBox.getItemAt(0)
        }
        versionComboBox.selectedIndex = indexOfValue
    }

    override fun applyEditorTo(bukkitRunConfiguration: BukkitRunConfiguration) {
        bukkitRunConfiguration.runOptions = runOptionsField.text
        bukkitRunConfiguration.minecraftVersion = versionComboBox.selectedItem as String
    }

    override fun createEditor() = panel

    private fun createUIComponents() {
        loadVersions()
    }
    private fun loadVersions() {
        if (versionsLoaded) {
            return
        }

        versionsLoaded = true
        CoroutineScope(Dispatchers.Swing).launch {
            try {
                withContext(Dispatchers.IO) { getVersionSelector(configuration.platformType) }.set(versionComboBox)
            } catch (e: Exception) {
                errorLabel.isVisible = true
            }
        }
    }
}
