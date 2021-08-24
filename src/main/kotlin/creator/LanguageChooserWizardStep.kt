/*
 * Minecraft Dev for IntelliJ
 *
 * https://minecraftdev.org
 *
 * Copyright (c) 2021 minecraft-dev
 *
 * MIT License
 */

package creator

import com.demonwav.mcdev.creator.MinecraftProjectCreator
import com.demonwav.mcdev.language.LanguageType
import com.intellij.ide.util.projectWizard.ModuleWizardStep
import javax.swing.AbstractListModel
import javax.swing.JComponent
import javax.swing.JLabel
import javax.swing.JList
import javax.swing.JPanel
import javax.swing.ListCellRenderer
import javax.swing.SwingConstants

class LanguageChooserWizardStep(private val creator: MinecraftProjectCreator) : ModuleWizardStep() {
    private lateinit var panel: JPanel
    private lateinit var languageList: JList<LanguageType>

    override fun getComponent(): JComponent = panel

    init {
        updateStep()
    }

    override fun isStepVisible(): Boolean = getSupportedLanguages().size > 1
    override fun updateStep() {
        val oldSelectedIndex = languageList.selectedIndex
        languageList.model = object : AbstractListModel<LanguageType>() {
            override fun getSize(): Int = getSupportedLanguages().size
            override fun getElementAt(index: Int): LanguageType = getSupportedLanguages()[index]
        }
        languageList.cellRenderer = ListCellRenderer<LanguageType> { _, value, _, _, _ ->
            JLabel(value.getInstance().displayName, value.getInstance().icon, SwingConstants.LEADING)
        }
        languageList.selectedIndex =
            if (oldSelectedIndex < languageList.model.size && oldSelectedIndex != -1) oldSelectedIndex
            else 0
    }

    override fun validate(): Boolean {
        return !languageList.isSelectionEmpty
    }

    override fun updateDataModel() {
        creator.configs.forEach {
            it.language = languageList.selectedValue
        }
    }

    fun getSupportedLanguages(): List<LanguageType> {
        var result = LanguageType.values().toMutableSet()
        creator.configs.forEach {
            result = result.intersect(it.type.type.supportedLanguages).toMutableSet()
        }
        return result.toMutableList().sorted()
    }
}
