[![version](https://img.shields.io/maven-central/v/ru.astrainteractive.klibs/kdi?style=flat-square)](https://github.com/makeevrserg/KDI)
[![kotlin_version](https://img.shields.io/badge/kotlin-1.9.0-blueviolet?style=flat-square)](https://github.com/makeevrserg/KDI)

## KDI

KDI is super lightweight Kotlin Multiplatform Manual DI library

## Installation

Gradle

```kotlin
implementation("ru.astrainteractive.klibs:kdi:<version>")
```

Version catalogs

```toml
[versions]
klibs-kdi = "<latest-version>"

[libraries]
klibs-kdi = { module = "ru.astrainteractive.klibs:kdi", version.ref = "klibs-kdi" }
```

See also [MobileX](https://github.com/makeevrserg/MobileX) as parent library for more useful kotlin code

## Declaring a module

Firstly, create a module interface, which will contains necessary dependencies

```kotlin
/**
 * This is your module with two dependencies
 */
interface PluginModule : Module {
    val simpleDatabase: Single<Database>
    val pluginTranslation: Single<PluginTranslation>

    class Impl : PluginModule {
        override val simpleDatabase: Single<Database> = Single {
            TODO()
        }
        override val pluginTranslation: Single<PluginTranslation> = Single {
            TODO()
        }
    }
}

/**
 * Or you can use delegation to get rid of [Single] class
 */
interface DelegatePluginModule : Module {
    val simpleDatabase: Database
    val pluginTranslation: PluginTranslation

    class Impl : DelegatePluginModule {
        val simpleDatabase: Database by Single { TODO() }
        val pluginTranslation: PluginTranslation by Single { TODO() }
    }
}
```

## Using module in function

```kotlin
/**
 * This is your function, in which you need [PluginModule.simpleDatabase]
 * and [PluginModule.pluginTranslation]
 */
fun myPluginFunction(module: PluginModule) {
    TODO()
}
```

## Using SubModules

```kotlin
/**
 * This is our custom subModule, which contains factory, which will return random UUID
 */
class SubModule : Module {
    val randomUUID = Factory {
        UUID.randomUUID()
    }
}

/**
 * This is our root module
 */
object RootModule : Module {
    /**
     * Here we getting via kotlin's ReadProperty SubModule;
     */
    private val subModule = SubModule()

    /**
     * Here we remember uuid, provided by SubModule's factory
     */
    val uuid = Single {
        subModule.randomUUID.create()
    }
}
```

That's it! As easy as it looks

## DI Components

- `Dependency` - is an interface which has getValueProperty, so can be used by `val expression by dependency`
- `Factory` - is a fun interface which can build data for your classes
- `Lateinit` - is used for components which can't be initialized internall
- `Module` - is an interface is a definition for module package, which will contains
- `Provider` - is a fun interface which can provider some data for your dependency
- `Reloadable` - can be used to create reloadable components with kotlin object
- `Single` - is a singleton value which will be a unique and single instant

## Experimental WiredModule
Dependencies can be remembered via WiredModule
```kotlin
class RootWiredModule : WiredModule by WiredModule.Default() {
    val intSingle = Single { 10 }.remember()
    val intProvider = Provider { 11 }.remember()
    val intFactory = Factory { 13 }.remember()
    val intReloadable = Reloadable { Random.nextInt() }.remember()
}

class MySubModule(
    private val rootWiredModule: RootWiredModule
) : Module {
    val singleInt: Int by rootWiredModule.single()
    val intProvider: Int by rootWiredModule.provider()
    val intFactory: Int = rootWiredModule.factory<Int>().create()
    val intReloadable: Int by rootWiredModule.reloadable()
}
```