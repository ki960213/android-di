package com.ki960213.sheath

import com.ki960213.sheath.component.SheathComponent
import com.ki960213.sheath.sorter.sorted
import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.full.isSupertypeOf

class SheathContainer private constructor(private val components: Set<SheathComponent>) {

    operator fun get(
        type: KType,
        qualifier: KClass<*>? = null,
    ): SheathComponent {
        val dependingComponents =
            components.filter { type.isSupertypeOf(it.type) && it.qualifier == qualifier }
        require(dependingComponents.size == 1) { "종속 항목이 모호하거나 존재하지 않습니다." }
        return dependingComponents.first()
    }

    companion object {
        fun from(components: List<SheathComponent>): SheathContainer {
            val distinctComponents = components.toSet()
            require(distinctComponents.size == components.size) { "중복된 컴포넌트가 존재합니다." }

            val initializedComponents = distinctComponents.sorted()
                .fold(mutableListOf<SheathComponent>()) { acc, component ->
                    component.initialize(acc)
                    acc.add(component)
                    acc
                }
            return SheathContainer(initializedComponents.toSet())
        }
    }
}
