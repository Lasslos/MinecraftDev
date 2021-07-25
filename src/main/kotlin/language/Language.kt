package com.demonwav.mcdev.language

import javax.swing.Icon

abstract class Language {
    abstract val displayName: String
    abstract val icon: Icon
    abstract val fileEnding: String
}