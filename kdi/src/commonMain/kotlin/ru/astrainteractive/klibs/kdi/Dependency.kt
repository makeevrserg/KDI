package ru.astrainteractive.klibs.kdi

import kotlin.reflect.KProperty

/**
 * [Dependency] is a base interface for [Lateinit] or [Single]
 */
interface Dependency<out T> {
    val value: T
}

inline operator fun <reified T, K> Dependency<T>.getValue(t: K?, property: KProperty<*>): T = value
