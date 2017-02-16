/*
 * Minecraft Dev for IntelliJ
 *
 * https://minecraftdev.org
 *
 * Copyright (c) 2017 minecraft-dev
 *
 * MIT License
 */

package com.demonwav.mcdev.util

import com.intellij.psi.PsiAnnotation
import com.intellij.psi.PsiAnnotationMemberValue
import com.intellij.psi.PsiArrayInitializerMemberValue
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiClassObjectAccessExpression
import com.intellij.psi.PsiClassType
import com.intellij.psi.PsiElement
import org.jetbrains.annotations.Contract

@Contract(pure = true)
fun PsiAnnotationMemberValue.computeStringArray(): List<String> {
    return parseArray { it.constantStringValue }
}

@Contract(pure = true)
fun PsiAnnotationMemberValue.resolveClassArray(): List<PsiClass> {
    return parseArray { it.resolveClass() }
}

@Contract(pure = true)
private fun PsiAnnotationMemberValue.resolveClass(): PsiClass? {
    if (this !is PsiClassObjectAccessExpression) {
        return null
    }

    return (operand.type as PsiClassType).resolve()
}

@Contract(pure = true)
private inline fun <T : Any> PsiAnnotationMemberValue.parseArray(func: (PsiAnnotationMemberValue) -> T?): List<T> {
    return if (this is PsiArrayInitializerMemberValue) {
        initializers.mapNotNull(func)
    } else {
        return listOfNotNull(func(this))
    }
}

// PsiNameValuePair -> PsiAnnotationParameterList -> PsiAnnotation
@get:Contract(pure = true)
val PsiElement.annotationFromNameValuePair
    get() = parent?.parent as? PsiAnnotation

// value -> PsiNameValuePair -> see above
@get:Contract(pure = true)
val PsiElement.annotationFromValue
    get() = parent?.annotationFromNameValuePair

// value -> PsiArrayInitializerMemberValue -> PsiNameValuePair -> see above
@get:Contract(pure = true)
val PsiElement.annotationFromArrayValue: PsiAnnotation?
    get() {
        val parent = parent ?: return null
        return if (parent is PsiArrayInitializerMemberValue) {
            parent.parent?.annotationFromNameValuePair
        } else {
            parent.annotationFromNameValuePair
        }
    }
