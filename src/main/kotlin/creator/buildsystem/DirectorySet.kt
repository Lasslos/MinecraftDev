/*
 * Minecraft Dev for IntelliJ
 *
 * https://minecraftdev.org
 *
 * Copyright (c) 2021 minecraft-dev
 *
 * MIT License
 */

package com.demonwav.mcdev.creator.buildsystem

import com.demonwav.mcdev.language.LanguageType
import java.nio.file.Files
import java.nio.file.Path

data class DirectorySet(
    val sourceDirectory: Path,
    val resourceDirectory: Path,
    val testSourceDirectory: Path,
    val testResourceDirectory: Path
) {
    companion object {
        fun create(dir: Path, languageType: LanguageType = LanguageType.JAVA): DirectorySet {
            val sourceDirectory = dir.resolve("src/main/" + if (languageType == LanguageType.JAVA) "java" else "kotlin")
            val resourceDirectory = dir.resolve("src/main/resources")
            val testSourceDirectory =
                dir.resolve("src/test/" + if (languageType == LanguageType.JAVA) "java" else "kotlin")
            val testResourceDirectory = dir.resolve("src/test/resources")
            Files.createDirectories(sourceDirectory)
            Files.createDirectories(resourceDirectory)
            Files.createDirectories(testSourceDirectory)
            Files.createDirectories(testResourceDirectory)
            return DirectorySet(sourceDirectory, resourceDirectory, testSourceDirectory, testResourceDirectory)
        }
    }
}
