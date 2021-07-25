package com.demonwav.mcdev.language

import com.demonwav.mcdev.language.java.JavaLanguage
import com.demonwav.mcdev.language.kotlin.KotlinLanguage

enum class LanguageType {
    JAVA {
        override fun getInstance() = JavaLanguage
    },
    KOTLIN {
        override fun getInstance() = KotlinLanguage
    };

    abstract fun getInstance(): Language
}