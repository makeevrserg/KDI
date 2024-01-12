package ru.astrainteractive.klibs.kdi

import kotlin.reflect.KProperty

/**
 * [Module] interface is a definition for module package, which will contains
 * other dependencies or even submodules which are also will be [Module]
 */
@Deprecated("This interface is useless")
interface Module

inline operator fun <reified T : Module, K> T.getValue(t: K?, property: KProperty<*>): T = this
