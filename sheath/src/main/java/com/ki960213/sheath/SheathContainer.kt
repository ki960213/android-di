package com.ki960213.sheath

import com.ki960213.sheath.component.SheathComponent
import com.ki960213.sheath.sorter.sorted
import kotlin.reflect.KClass
import kotlin.reflect.KType

class SheathContainer private constructor(private val components: Set<SheathComponent>) {

    operator fun get(
        type: KType,
        qualifier: KClass<*>? = null,
    ): SheathComponent {
        return components.find { it.type == type && it.qualifier == qualifier }
            ?: throw IllegalArgumentException(if (qualifier == null) "" else "한정자가 ${qualifier.simpleName}인 " + "$type 타입의 컴포넌트가 등록되지 않았습니다.")
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
