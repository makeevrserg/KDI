package ru.astrainteractive.klibs.kdi.wired

import co.touchlab.kermit.Logger
import ru.astrainteractive.klibs.kdi.ExperimentalKDIApi
import ru.astrainteractive.klibs.kdi.Factory
import ru.astrainteractive.klibs.kdi.Lateinit
import ru.astrainteractive.klibs.kdi.Provider
import ru.astrainteractive.klibs.kdi.Reloadable
import ru.astrainteractive.klibs.kdi.Single
import kotlin.reflect.KClass

/**
 * This is default implementation of [WiredModule] which can be used with delegation
 */
@OptIn(ExperimentalKDIApi::class)
@Suppress("TooManyFunctions")
internal class DefaultWiredModule : WiredModule {

    private val wires = HashSet<Wire<*>>()
    private inline fun <T : Any> rememberWire(wire: Wire<T>) {
        if (wires.contains(wire)) error("Factory set already contains $this")
        Logger.i(tag = TAG) { "Remembered Factory $this" }
        wires.add(wire)
    }

    override fun <T : Any> Factory<T>.remember(clazz: KClass<T>): Factory<T> {
        Wire.Factory(clazz, this).run(::rememberWire)
        return this
    }

    override fun <T : Any> Provider<T>.remember(clazz: KClass<T>): Provider<T> {
        Wire.Provider(clazz, this).run(::rememberWire)
        return this
    }

    override fun <T : Any, K : Single<T>> K.remember(clazz: KClass<T>): K {
        Wire.Dependency.Single(clazz, this).run(::rememberWire)
        return this
    }

    override fun <T : Any, K : Lateinit<T>> K.remember(clazz: KClass<T>): K {
        Wire.Dependency.Lateinit(clazz, this).run(::rememberWire)
        return this
    }

    override fun <T : Any, K : Reloadable<T>> K.remember(clazz: KClass<T>): K {
        Wire.Dependency.Reloadable(clazz, this).run(::rememberWire)
        return this
    }

    override fun <T : Any> single(kClass: KClass<T>): Single<T> {
        return wires.filterIsInstance<Wire.Dependency.Single<T>>().first {
            it.kClass == kClass
        }.instance
    }

    override fun <T : Any> reloadable(kClass: KClass<T>): Reloadable<T> {
        return wires.filterIsInstance<Wire.Dependency.Reloadable<T>>().first {
            it.kClass == kClass
        }.instance
    }

    override fun <T : Any> lateinit(kClass: KClass<T>): Lateinit<T> {
        return wires.filterIsInstance<Wire.Dependency.Lateinit<T>>().first {
            it.kClass == kClass
        }.instance
    }

    override fun <T : Any> factory(kClass: KClass<T>): Factory<T> {
        return wires.filterIsInstance<Wire.Factory<T>>().first {
            it.kClass == kClass
        }.instance
    }

    override fun <T : Any> provider(kClass: KClass<T>): Provider<T> {
        return wires.filterIsInstance<Wire.Provider<T>>().first {
            it.kClass == kClass
        }.instance
    }

    companion object {
        private const val TAG = "WiredModule"
    }
}
