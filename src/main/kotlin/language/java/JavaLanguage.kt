package com.demonwav.mcdev.language.java

import com.demonwav.mcdev.asset.LanguageAssets
import com.demonwav.mcdev.language.Language
import javax.swing.Icon

object JavaLanguage : Language() {
    override val displayName: String = "Java"
    override val icon: Icon = LanguageAssets.JAVA_ICON
    override val fileEnding: String = ".java"
}