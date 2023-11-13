package com.ki960213.sheath

import com.ki960213.sheath.component.SheathComponent
import com.ki960213.sheath.sorter.sorted
import kotlin.reflect.KType
import kotlin.reflect.full.isSupertypeOf
import kotlin.reflect.jvm.jvmErasure

class SheathContainer(private val components: Map<KType, SheathComponent>) {

    operator fun get(type: KType): SheathComponent {
        return components[type]
            ?: components.values.find { component -> type.isSupertypeOf(component.type) }
            ?: throw IllegalArgumentException("${type.jvmErasure.qualifiedName} 클래스가 컴포넌트로 등록되지 않았습니다.")
    }

    companion object {
        fun from(components: List<SheathComponent>): SheathContainer {
            val initializedComponents = components.sorted()
                .fold(mutableMapOf<KType, SheathComponent>()) { acc, component ->
                    component.instantiate(acc.values.toList())
                    acc[component.type] = component
                    acc
                }
            return SheathContainer(initializedComponents)
        }
    }
}
