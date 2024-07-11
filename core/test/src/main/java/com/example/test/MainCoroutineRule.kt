package com.example.test

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

/**
 * Устанавливает [TestDispatcher] для [Dispatchers.Main].
 *
 * При использовании [MainCoroutineRule] необходимо вызывать runTest:
 *
 * ```
 * @Test
 * fun usingRunTest() = runTest {
 *     aTestCoroutine()
 * }
 * ```
 *
 * @param testDispatcher если параметр передан, то будет использоваться переданный [TestDispatcher],
 * иначе [UnconfinedTestDispatcher].
 * Чтобы запускать корутины лениво передать [StandardTestDispatcher]
 * и вызывать advanceUntilIdle() для запуска ожидающих корутин.
 *
 */
@ExperimentalCoroutinesApi
open class MainCoroutineRule(
    val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()
) : TestWatcher() {

    val testScope = CoroutineScope(testDispatcher + Job())

    override fun starting(description: Description) {
        super.starting(description)
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description) {
        super.finished(description)
        Dispatchers.resetMain()
    }
}
