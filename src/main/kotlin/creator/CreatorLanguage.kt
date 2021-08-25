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

import com.demonwav.mcdev.asset.LanguageAssets
import javax.swing.Icon

enum class CreatorLanguage(val displayName: String, val icon: Icon, val fileEnding: String, val directory: String) {
    JAVA("Java", LanguageAssets.JAVA_ICON, ".java", "java"),
    KOTLIN("Kotlin", LanguageAssets.KOTLIN_ICON, ".kt", "kotlin");
}
