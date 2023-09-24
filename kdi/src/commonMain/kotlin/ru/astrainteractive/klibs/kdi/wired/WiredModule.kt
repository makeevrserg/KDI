package ru.astrainteractive.klibs.kdi.wired

import ru.astrainteractive.klibs.kdi.ExperimentalKDIApi
import ru.astrainteractive.klibs.kdi.Factory
import ru.astrainteractive.klibs.kdi.Lateinit
import ru.astrainteractive.klibs.kdi.Module
import ru.astrainteractive.klibs.kdi.Provider
import ru.astrainteractive.klibs.kdi.Reloadable
import ru.astrainteractive.klibs.kdi.Single
import kotlin.reflect.KClass

/**
 * [WiredModule] is used to minimize future changes in other module by just declaring what you need
 */
@ExperimentalKDIApi
interface WiredModule : Module {
    /**
     * This will remember and return current factory instance
     */
    fun <T : Any> Factory<T>.remember(clazz: KClass<T>): Factory<T>

    /**
     * This will remember and return current provider instance
     */
    fun <T : Any> Provider<T>.remember(clazz: KClass<T>): Provider<T>

    /**
     * This will remember and return current single instance
     */
    fun <T : Any, K : Single<T>> K.remember(clazz: KClass<T>): K

    /**
     * This will remember and return current lateinit instance
     */
    fun <T : Any, K : Lateinit<T>> K.remember(clazz: KClass<T>): K

    /**
     * This will remember and return current reloadable instance
     */
    fun <T : Any, K : Reloadable<T>> K.remember(clazz: KClass<T>): K

    /**
     * This will return single or throws [IllegalStateException] if not found
     */
    fun <T : Any> single(kClass: KClass<T>): Single<T>

    /**
     * This will return reloadable or throws [IllegalStateException] if not found
     */
    fun <T : Any> reloadable(kClass: KClass<T>): Reloadable<T>

    /**
     * This will return reloadable or throws [IllegalStateException] if not found
     */
    fun <T : Any> lateinit(kClass: KClass<T>): Lateinit<T>

    /**
     * This will return factory or throws [IllegalStateException] if not found
     */
    fun <T : Any> factory(kClass: KClass<T>): Factory<T>

    /**
     * This will return provider or throws [IllegalStateException] if not found
     */
    fun <T : Any> provider(kClass: KClass<T>): Provider<T>

    companion object {

        fun wire(): WiredModule = DefaultWiredModule()
    }
}
