package ru.astrainteractive.klibs.kdi.wired

import ru.astrainteractive.klibs.kdi.ExperimentalKDIApi
import ru.astrainteractive.klibs.kdi.Factory
import ru.astrainteractive.klibs.kdi.Lateinit
import ru.astrainteractive.klibs.kdi.Provider
import ru.astrainteractive.klibs.kdi.Reloadable
import ru.astrainteractive.klibs.kdi.Single

/**
 * This will remember and return current factory instance
 */
@OptIn(ExperimentalKDIApi::class)
inline fun <reified T : Any> WiredModule.remember(instance: Factory<T>): Factory<T> {
    return instance.remember(T::class)
}

/**
 * This will remember and return current lateinit instance
 */
@OptIn(ExperimentalKDIApi::class)
inline fun <reified T : Any> WiredModule.remember(instance: Lateinit<T>): Lateinit<T> {
    return instance.remember(T::class)
}

/**
 * This will remember and return current provider instance
 */
@OptIn(ExperimentalKDIApi::class)
inline fun <reified T : Any> WiredModule.remember(instance: Provider<T>): Provider<T> {
    return instance.remember(T::class)
}

/**
 * This will remember and return current reloadable instance
 */
@OptIn(ExperimentalKDIApi::class)
inline fun <reified T : Any> WiredModule.remember(instance: Reloadable<T>): Reloadable<T> {
    return instance.remember(T::class)
}

/**
 * This will remember and return current single instance
 */
@OptIn(ExperimentalKDIApi::class)
inline fun <reified T : Any> WiredModule.remember(instance: Single<T>): Single<T> {
    return instance.remember(T::class)
}

/**
 * This will return factory or throws [IllegalStateException] if not found
 */
@OptIn(ExperimentalKDIApi::class)
inline fun <reified T : Any> WiredModule.factory(): Factory<T> {
    return factory(T::class)
}

/**
 * This will return reloadable or throws [IllegalStateException] if not found
 */
@OptIn(ExperimentalKDIApi::class)
inline fun <reified T : Any> WiredModule.lateinit(): Lateinit<T> {
    return lateinit(T::class)
}

/**
 * This will return provider or throws [IllegalStateException] if not found
 */
@OptIn(ExperimentalKDIApi::class)
inline fun <reified T : Any> WiredModule.provider(): Provider<T> {
    return provider(T::class)
}

/**
 * This will return reloadable or throws [IllegalStateException] if not found
 */
@OptIn(ExperimentalKDIApi::class)
inline fun <reified T : Any> WiredModule.reloadable(): Reloadable<T> {
    return reloadable(T::class)
}

/**
 * This will return single or throws [IllegalStateException] if not found
 */
@OptIn(ExperimentalKDIApi::class)
inline fun <reified T : Any> WiredModule.single(): Single<T> {
    return single(T::class)
}
