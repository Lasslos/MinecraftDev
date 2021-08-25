/*
 * Minecraft Dev for IntelliJ
 *
 * https://minecraftdev.org
 *
 * Copyright (c) 2021 minecraft-dev
 *
 * MIT License
 */

package com.demonwav.mcdev.creator

import com.demonwav.mcdev.creator.buildsystem.BuildSystem
import com.demonwav.mcdev.creator.buildsystem.BuildSystemType
import com.demonwav.mcdev.creator.exception.EmptyFieldSetupException
import com.demonwav.mcdev.creator.exception.OtherSetupException
import com.demonwav.mcdev.creator.exception.SetupException
import com.intellij.ide.util.projectWizard.ModuleWizardStep
import com.intellij.openapi.ui.MessageType
import com.intellij.openapi.ui.popup.Balloon
import com.intellij.openapi.ui.popup.JBPopupFactory
import com.intellij.ui.awt.RelativePoint
import java.awt.Component
import javax.swing.DefaultListCellRenderer
import javax.swing.JComboBox
import javax.swing.JList
import javax.swing.JPanel
import javax.swing.JTextField

class BuildSystemWizardStep(private val creator: MinecraftProjectCreator) : ModuleWizardStep() {

    private lateinit var groupIdField: JTextField
    private lateinit var artifactIdField: JTextField
    private lateinit var versionField: JTextField
    private lateinit var panel: JPanel
    private lateinit var buildSystemBox: JComboBox<BuildSystemType>
    private lateinit var languageBox: JComboBox<CreatorLanguage>

    override fun getComponent() = panel

    override fun updateStep() {
        updateBuildSystemBox()
        updateLanguageBox()
    }

    private fun updateBuildSystemBox() {
        val previousBuildSystem = buildSystemBox.selectedItem
        buildSystemBox.removeAllItems()
        buildSystemBox.isEnabled = true

        val types = BuildSystemType.values().filter { type ->
            creator.configs.all { type.creatorType.isInstance(it) }
        }

        for (type in types) {
            buildSystemBox.addItem(type)
        }

        if (buildSystemBox.itemCount == 1) {
            buildSystemBox.isEnabled = false
            return
        }

        if (previousBuildSystem != null) {
            buildSystemBox.selectedItem = previousBuildSystem
            return
        }

        buildSystemBox.selectedIndex = 0

        // We prefer Gradle, so if it's included, choose it
        // If Gradle is not included, luck of the draw
        if (creator.configs.any { it.preferredBuildSystem == BuildSystemType.GRADLE }) {
            buildSystemBox.selectedItem = BuildSystemType.GRADLE
            return
        }

        val counts = creator.configs.asSequence()
            .mapNotNull { it.preferredBuildSystem }
            .groupingBy { it }
            .eachCount()

        val maxValue = counts.maxByOrNull { it.value }?.value ?: return
        counts.asSequence()
            .filter { it.value == maxValue }
            .map { it.key }
            .firstOrNull()
            ?.let { mostPopularType ->
                buildSystemBox.selectedItem = mostPopularType
            }
    }

    private fun updateLanguageBox() {
        val oldSelectedIndex = languageBox.selectedIndex

        languageBox.removeAllItems()
        getSupportedLanguages().forEach {
            languageBox.addItem(it)
        }

        languageBox.renderer = LanguageCellRenderer

        //If there is more than one item, enable it. Otherwise, don't.
        languageBox.isEnabled = languageBox.itemCount > 1

        languageBox.selectedIndex = when (languageBox.itemCount) {
            0 -> -1
            1 -> 0
            else -> {
                if (oldSelectedIndex >= 0 && oldSelectedIndex < languageBox.itemCount) {
                    oldSelectedIndex
                } else {
                    0
                }
            }
        }
    }

    private fun getSupportedLanguages(): List<CreatorLanguage> {
        var result = CreatorLanguage.values().toSet()
        creator.configs.forEach {
            result = result.intersect(it.supportedLanguages)
        }
        return result.sorted()
    }

    override fun updateDataModel() {
        creator.buildSystem = createBuildSystem()

        creator.configs.forEach {
            it.language = languageBox.selectedItem as CreatorLanguage
        }
    }

    private fun createBuildSystem(): BuildSystem {
        val type = buildSystemBox.selectedItem as? BuildSystemType
            ?: throw IllegalStateException("Selected item is not a ${BuildSystemType::class.java.name}")

        return type.create(groupIdField.text, artifactIdField.text, versionField.text)
    }

    override fun validate(): Boolean {
        try {
            if (groupIdField.text.isEmpty()) {
                throw EmptyFieldSetupException(groupIdField)
            }

            if (artifactIdField.text.isEmpty()) {
                throw EmptyFieldSetupException(artifactIdField)
            }

            if (versionField.text.isBlank()) {
                throw EmptyFieldSetupException(versionField)
            }

            if (!groupIdField.text.matches(NO_WHITESPACE)) {
                throw OtherSetupException("The GroupId field cannot contain any whitespace", groupIdField)
            }

            if (!artifactIdField.text.matches(NO_WHITESPACE)) {
                throw OtherSetupException("The ArtifactId field cannot contain any whitespace", artifactIdField)
            }
        } catch (e: SetupException) {
            JBPopupFactory.getInstance().createHtmlTextBalloonBuilder(e.error, MessageType.ERROR, null)
                .setFadeoutTime(2000)
                .createBalloon()
                .show(RelativePoint.getSouthWestOf(e.j), Balloon.Position.below)
            return false
        }

        return true
    }

    object LanguageCellRenderer : DefaultListCellRenderer() {
        override fun getListCellRendererComponent(
            list: JList<*>?,
            value: Any?,
            index: Int,
            isSelected: Boolean,
            cellHasFocus: Boolean
        ): Component {
            val displayValue = (value as? CreatorLanguage)?.displayName
            return super.getListCellRendererComponent(list, displayValue, index, isSelected, cellHasFocus)
        }
    }

    companion object {
        val NO_WHITESPACE = Regex("\\S+")
    }
}
