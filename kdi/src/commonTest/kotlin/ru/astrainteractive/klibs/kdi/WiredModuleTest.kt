package ru.astrainteractive.klibs.kdi

import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals

class WiredModuleTest {
    @OptIn(ExperimentalKDIApi::class)
    @Test
    fun testManualInjection() {
        val rootModule = object : WiredModule by WiredModule.Default() {
            val intSingle = Single { 10 }.remember()
            val intProvider = Provider { 11 }.remember()
            val intFactory = Factory { 13 }.remember()
            val intReloadable = Reloadable { Random.nextInt() }.remember()
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
}
