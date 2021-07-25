package com.demonwav.mcdev.language.kotlin

import com.demonwav.mcdev.asset.LanguageAssets
import com.demonwav.mcdev.language.Language
import javax.swing.Icon

object KotlinLanguage : Language() {
    override val displayName: String = "Kotlin/JVM"
    override val icon: Icon = LanguageAssets.KOTLIN_ICON
    override val fileEnding: String = ".kt"
}