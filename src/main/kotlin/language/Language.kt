/*
 * Minecraft Dev for IntelliJ
 *
 * https://minecraftdev.org
 *
 * Copyright (c) 2021 minecraft-dev
 *
 * MIT License
 */

package com.demonwav.mcdev.language

import javax.swing.Icon

abstract class Language {
    abstract val displayName: String
    abstract val icon: Icon
    abstract val fileEnding: String
}
