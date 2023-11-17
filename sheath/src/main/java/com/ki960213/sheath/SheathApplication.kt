package com.ki960213.sheath

import android.content.Context
import com.ki960213.sheath.component.ContextSheathComponent
import com.ki960213.sheath.component.SheathComponent
import com.ki960213.sheath.scanner.ComponentScanner

object SheathApplication {

    lateinit var sheathContainer: SheathContainer

    fun run(context: Context) {
        val scanner = ComponentScanner()
        val components: List<SheathComponent> =
            scanner.findAll() + ContextSheathComponent(context)

        sheathContainer = SheathContainer.from(components)
    }
}
