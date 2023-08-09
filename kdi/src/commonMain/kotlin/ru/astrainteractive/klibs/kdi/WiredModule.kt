package ru.astrainteractive.klibs.kdi

/**
 * [WiredModule] is used to minimize future changes in other module by just declaring what you need
 */
@ExperimentalKDIApi
interface WiredModule : Module {
    /**
     * This will remember and return current factory instance
     */
    fun <T> Factory<T>.remember(): Factory<T>

    /**
     * This will remember and return current provider instance
     */
    fun <T> Provider<T>.remember(): Provider<T>

    /**
     * This will remember and return current dependency instance
     */
    fun <T : Any, K : Dependency<T>> K.remember(): K

    /**
     * This will return single or throws [IllegalStateException] if not found
     */
    fun <T : Any> single(): Single<T>

    /**
     * This will return single lateinit or throws [IllegalStateException] if not found
     */
    fun <T : Any> lateinit(): Lateinit<T>

    /**
     * This will return reloadable or throws [IllegalStateException] if not found
     */
    fun <T : Any> reloadable(): Reloadable<T>

    /**
     * This will return factory or throws [IllegalStateException] if not found
     */
    fun <T : Any> factory(): Factory<T>

    /**
     * This will return provider or throws [IllegalStateException] if not found
     */
    fun <T : Any> provider(): Provider<T>

    /**
     * This is default implementation of [WiredModule] which can be used with delegation
     */
    class Default : WiredModule {
        private val factories = HashSet<Factory<*>>()
        private val providers = HashSet<Provider<*>>()
        private val dependencies = HashSet<Dependency<*>>()

        override fun <T> Factory<T>.remember(): Factory<T> {
            if (factories.contains(this)) error("Factory set already contains $this")
            factories.add(this)
            return this
        }

        override fun <T> Provider<T>.remember(): Provider<T> {
            if (providers.contains(this)) error("Factory set already contains $this")
            providers.add(this)
            return this
        }

        override fun <T : Any, K : Dependency<T>> K.remember(): K {
            if (dependencies.contains(this)) error("Factory set already contains $this")
            dependencies.add(this)
            return this
        }

        override fun <T : Any> single(): Single<T> {
            return dependencies.filterIsInstance<Single<T>>().first()
        }

        override fun <T : Any> lateinit(): Lateinit<T> {
            return dependencies.filterIsInstance<Lateinit<T>>().first()
        }

        override fun <T : Any> reloadable(): Reloadable<T> {
            return dependencies.filterIsInstance<Reloadable<T>>().first()
        }

        override fun <T : Any> factory(): Factory<T> {
            return factories.filterIsInstance<Factory<T>>().first()
        }

        override fun <T : Any> provider(): Provider<T> {
            return providers.filterIsInstance<Provider<T>>().first()
        }
    }
}
