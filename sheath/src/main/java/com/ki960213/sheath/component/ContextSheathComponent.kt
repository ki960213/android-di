package com.ki960213.sheath.component

import android.content.Context
import kotlin.reflect.KType
import kotlin.reflect.full.createType

internal class ContextSheathComponent(
    private val context: Context,
) : SheathComponent() {
    override val type: KType = Context::class.createType()

    override val qualifier: Annotation? = null

    override val isSingleton: Boolean = true

    override val dependencies: Set<Dependency> = emptySet()

    override fun getNewInstance(): Any = context
}
