package com.ki960213.sheath

import com.ki960213.sheath.component.SheathComponent
import kotlin.reflect.KType
import kotlin.reflect.full.isSupertypeOf
import kotlin.reflect.jvm.jvmErasure

class SheathContainer(private val components: Map<KType, SheathComponent>) {

    operator fun get(type: KType): SheathComponent {
        return components[type]
            ?: components.values.find { component -> type.isSupertypeOf(component.type) }
            ?: throw IllegalArgumentException("${type.jvmErasure.qualifiedName} 클래스가 컴포넌트로 등록되지 않았습니다.")
    }
}
