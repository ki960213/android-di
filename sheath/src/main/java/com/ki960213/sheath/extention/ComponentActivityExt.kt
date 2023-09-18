package com.ki960213.sheath.extention

import androidx.activity.ComponentActivity
import androidx.annotation.MainThread
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelLazy
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.ki960213.sheath.SheathApplication
import kotlin.reflect.full.createType

@MainThread
inline fun <reified VM : ViewModel> ComponentActivity.viewModels(
    noinline extrasProducer: (() -> CreationExtras)? = null,
    noinline factoryProducer: (() -> ViewModelProvider.Factory)? = null,
): Lazy<VM> {
    val viewModelFactory = viewModelFactory {
        initializer {
            val viewModelComponent =
                SheathApplication.sheathComponentContainer[VM::class.createType()]
            viewModelComponent.getNewInstance() as VM
        }
    }

    return ViewModelLazy(
        VM::class,
        { viewModelStore },
        factoryProducer ?: { viewModelFactory },
        { extrasProducer?.invoke() ?: this.defaultViewModelCreationExtras },
    )
}
