package ru.astrainteractive.klibs.kdi.wired

import kotlin.reflect.KClass

internal sealed class Wire<T : Any>(val kClass: KClass<T>) {
    class Factory<T : Any>(
        kClass: KClass<T>,
        val instance: ru.astrainteractive.klibs.kdi.Factory<T>
    ) : Wire<T>(kClass)

    class Provider<T : Any>(
        kClass: KClass<T>,
        val instance: ru.astrainteractive.klibs.kdi.Provider<T>
    ) : Wire<T>(kClass)

    sealed class Dependency<T : Any, K : ru.astrainteractive.klibs.kdi.Dependency<T>>(
        kClass: KClass<T>,
        val instance: K
    ) : Wire<T>(kClass) {
        class Single<T : Any>(
            kClass: KClass<T>,
            instance: ru.astrainteractive.klibs.kdi.Single<T>
        ) : Dependency<T, ru.astrainteractive.klibs.kdi.Single<T>>(kClass, instance)

        class Reloadable<T : Any>(
            kClass: KClass<T>,
            instance: ru.astrainteractive.klibs.kdi.Reloadable<T>
        ) : Dependency<T, ru.astrainteractive.klibs.kdi.Reloadable<T>>(kClass, instance)

        class Lateinit<T : Any>(
            kClass: KClass<T>,
            instance: ru.astrainteractive.klibs.kdi.Lateinit<T>
        ) : Dependency<T, ru.astrainteractive.klibs.kdi.Lateinit<T>>(kClass, instance)
    }
}
