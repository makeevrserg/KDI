package ru.astrainteractive.klibs.kdi

import ru.astrainteractive.klibs.kdi.wired.WiredModule
import ru.astrainteractive.klibs.kdi.wired.factory
import ru.astrainteractive.klibs.kdi.wired.provider
import ru.astrainteractive.klibs.kdi.wired.reloadable
import ru.astrainteractive.klibs.kdi.wired.remember
import ru.astrainteractive.klibs.kdi.wired.single
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals

class WiredModuleTest {
    @OptIn(ExperimentalKDIApi::class)
    @Test
    fun testManualInjection() {
        val rootModule = object : WiredModule by WiredModule.wire() {
            val intSingle = Single { 10 }.run(::remember)
            val intProvider = Provider { 11 }.run(::remember)
            val intFactory = Factory { 13 }.run(::remember)
            val intReloadable = Reloadable { Random.nextInt() }.run(::remember)
        }
        val subModule = object : Module {
            val singleInt: Int by rootModule.single()
            val intProvider: Int by rootModule.provider()
            val intFactory: Int = rootModule.factory<Int>().create()
            val intReloadable: Int by rootModule.reloadable()
        }
        assertEquals(rootModule.intSingle.value, subModule.singleInt)
        assertEquals(rootModule.intProvider.provide(), subModule.intProvider)
        assertEquals(rootModule.intFactory.create(), subModule.intFactory)
        assertEquals(rootModule.intReloadable.value, subModule.intReloadable)
        rootModule.intReloadable.reload()
        assertEquals(rootModule.intReloadable.value, subModule.intReloadable)
    }

    interface Mapper<T> {
        fun toValue(): T
        class Impl<T>(val value: T) : Mapper<T> {
            override fun toValue(): T = value
        }
    }

    interface StringMapper : Mapper<String> {
        class Impl(val value: String) : StringMapper {
            override fun toValue(): String = value
        }
    }

    interface IntMapper : Mapper<Int> {
        class Impl(val value: Int) : IntMapper {
            override fun toValue(): Int = value
        }
    }

    @OptIn(ExperimentalKDIApi::class)
    @Test
    fun testCustomInterfaces() {
        val rootModule = object : WiredModule by WiredModule.wire() {
            val stringMapper = Single {
                StringMapper.Impl("StringMapper") as StringMapper
            }.run(::remember)
            val intMapper = Single {
                IntMapper.Impl(0) as IntMapper
            }.run(::remember)
            val doubleMapper = Single {
                Mapper.Impl(0.0) as Mapper<Double>
            }.run(::remember)
        }
        val subModule = object : Module {
            val stringMapper: StringMapper by rootModule.single()
            val intMapper: IntMapper by rootModule.single()
            val doubleMapper: Mapper<Double> by rootModule.single<Mapper<Double>>()
        }
        assertEquals(rootModule.intMapper.value, subModule.intMapper)
        assertEquals(rootModule.stringMapper.value, subModule.stringMapper)
        assertEquals(rootModule.doubleMapper.value, subModule.doubleMapper)
    }
}
