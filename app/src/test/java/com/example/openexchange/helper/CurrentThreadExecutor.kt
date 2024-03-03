package com.example.openexchange.helper

import java.util.concurrent.Callable
import java.util.concurrent.ExecutionException
import java.util.concurrent.ExecutorService
import java.util.concurrent.Future
import java.util.concurrent.FutureTask
import java.util.concurrent.TimeUnit
import java.util.stream.Collectors

/**
 * Executor which useful for unit testing
 */
class CurrentThreadExecutor : ExecutorService {
    override fun execute(command: Runnable) {
        command.run()
    }

    override fun shutdown() {}
    override fun shutdownNow(): List<Runnable> {
        return emptyList()
    }

    override fun isShutdown(): Boolean {
        return false
    }

    override fun isTerminated(): Boolean {
        return false
    }

    override fun awaitTermination(
        timeout: Long,
        unit: TimeUnit
    ): Boolean {
        return false
    }

    override fun <T> submit(task: Callable<T>): Future<T> {
        val f = FutureTask(task)
        f.run()
        return f
    }

    override fun <T> submit(
        task: Runnable,
        result: T
    ): Future<T> {
        val f = FutureTask(task, result)
        f.run()
        return f
    }

    override fun submit(task: Runnable): Future<*> {
        val f: FutureTask<*> =
            FutureTask<Void?>(task, null)
        f.run()
        return f
    }

    override fun <T> invokeAll(tasks: Collection<Callable<T>>): List<Future<T>> {
        return tasks.stream().map { task: Callable<T> -> this.submit(task) }
            .collect(Collectors.toList())
    }

    override fun <T> invokeAll(
        tasks: Collection<Callable<T>>,
        timeout: Long,
        unit: TimeUnit
    ): List<Future<T>> {
        return tasks.stream().map { task: Callable<T> -> this.submit(task) }
            .collect(Collectors.toList())
    }

    @Throws(
        InterruptedException::class,
        ExecutionException::class
    )
    override fun <T> invokeAny(tasks: Collection<Callable<T>>): T {
        return tasks.stream().map { task: Callable<T> -> this.submit(task) }
            .findFirst().get().get()
    }

    @Throws(
        InterruptedException::class,
        ExecutionException::class
    )
    override fun <T> invokeAny(
        tasks: Collection<Callable<T>>,
        timeout: Long,
        unit: TimeUnit
    ): T {
        return tasks.stream().map { task: Callable<T> -> this.submit(task) }
            .findFirst().get().get()
    }
}
