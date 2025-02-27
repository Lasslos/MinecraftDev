/*
 * Minecraft Development for IntelliJ
 *
 * https://mcdev.io/
 *
 * Copyright (C) 2023 minecraft-dev
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, version 3.0 only.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.demonwav.mcdev.translations.reference

import com.demonwav.mcdev.translations.TranslationConstants
import com.demonwav.mcdev.translations.TranslationFiles
import com.intellij.ide.util.gotoByName.ContributorsBasedGotoByModel
import com.intellij.navigation.ChooseByNameContributor
import com.intellij.navigation.NavigationItem
import com.intellij.openapi.progress.ProgressIndicator
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiNamedElement
import com.intellij.util.indexing.FindSymbolParameters
import java.util.TreeSet

class TranslationGotoModel(project: Project, private val prefix: String, private val suffix: String) :
    ContributorsBasedGotoByModel(
        project,
        arrayOf(
            ChooseByNameContributor.SYMBOL_EP_NAME.findExtensionOrFail(TranslationGotoSymbolContributor::class.java),
        ),
    ) {
    override fun acceptItem(item: NavigationItem?): Boolean {
        return TranslationFiles.getLocale(
            (item as PsiElement).containingFile?.virtualFile,
        ) == TranslationConstants.DEFAULT_LOCALE
    }

    override fun getElementsByName(
        name: String,
        parameters: FindSymbolParameters,
        canceled: ProgressIndicator,
    ): Array<Any> {
        val superResult = super.getElementsByName(name, parameters, canceled).asSequence()
        val result = TreeSet<PsiNamedElement> { o1, o2 ->
            (o1 as PsiNamedElement).name?.compareTo((o2 as PsiNamedElement).name ?: return@TreeSet -1) ?: -1
        }
        result.addAll(
            superResult.map { it as PsiNamedElement }.filter {
                val key = it.name ?: return@filter false
                key.startsWith(prefix) && key.endsWith(suffix)
            },
        )
        return result.toArray()
    }

    override fun getPromptText() = "Choose translation to use"

    override fun getNotInMessage() = "Couldn't find translation with that name"

    override fun getNotFoundMessage() = "Couldn't find translation with that name"

    override fun getCheckBoxName() = "Include non-project translations"

    override fun loadInitialCheckBoxState() = false

    override fun saveInitialCheckBoxState(state: Boolean) {
    }

    override fun getSeparators(): Array<String> = emptyArray()

    override fun getFullName(element: Any) = element.toString()

    override fun willOpenEditor() = false
}
