package com.ki960213.sheath.scanner

import com.ki960213.sheath.annotation.Component
import com.ki960213.sheath.annotation.DataSource
import com.ki960213.sheath.annotation.Module
import com.ki960213.sheath.annotation.Repository
import com.ki960213.sheath.annotation.SheathViewModel
import com.ki960213.sheath.annotation.UseCase
import com.ki960213.sheath.component.ClassSheathComponent
import com.ki960213.sheath.component.FunctionSheathComponent
import com.ki960213.sheath.component.SheathComponent
import com.ki960213.sheath.extention.hasAnnotationOrHasAttachedAnnotation
import org.atteo.classindex.ClassIndex
import kotlin.reflect.KClass
import kotlin.reflect.full.declaredMemberFunctions

internal class ComponentScanner {
    fun findAll(): List<SheathComponent> {
        val components = mutableListOf<SheathComponent>()
        val annotatedClasses = INDEXED_ANNOTATIONS.flatMap {
            ClassIndex.getAnnotated(it).map { clazz -> clazz.kotlin }
        }
        annotatedClasses.forEach {
            if (it.isComponent()) components.add(ClassSheathComponent(it))
            if (it.isModule()) components.addAll(it.extractSheathComponent())
        }
        return components
    }

    private fun KClass<*>.isComponent(): Boolean =
        this.annotations.any { hasAnnotationOrHasAttachedAnnotation<Component>() }

    private fun KClass<*>.isModule(): Boolean =
        this.annotations.any { it.annotationClass == Module::class }

    private fun KClass<*>.extractSheathComponent(): List<SheathComponent> =
        declaredMemberFunctions.mapNotNull { function ->
            if (function.hasAnnotationOrHasAttachedAnnotation<Component>()) {
                FunctionSheathComponent(function)
            } else {
                null
            }
        }

    companion object {
        private val INDEXED_ANNOTATIONS = listOf(
            Component::class.java,
            UseCase::class.java,
            Repository::class.java,
            DataSource::class.java,
            Module::class.java,
            SheathViewModel::class.java,
        )
    }
}
