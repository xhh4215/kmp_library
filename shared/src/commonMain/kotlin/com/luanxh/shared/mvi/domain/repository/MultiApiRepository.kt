package com.luanxh.shared.mvi.domain.repository

import com.example.shared.mvi.domain.data.DataResult
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

/***
 *  如何使用 将多个接口的数据整合返回
 *class HomeRepository(
 *     private val userRepo: UserRepository,
 *     private val orderRepo: OrderRepository,
 *     private val deviceRepo: DeviceRepository
 * ) : MultiApiRepository() {
 *
 *     suspend fun loadHome(): DataResult<HomeDomain> =
 *         combine3(
 *             callA = { userRepo.getUser() },
 *             callB = { orderRepo.getOrders() },
 *             callC = { deviceRepo.getDevices() }
 *         ) { user, orders, devices ->
 *             HomeDomain(user, orders, devices)
 *         }
 * }
 *
 */
abstract class MultiApiRepository : BaseRepository() {

    protected suspend fun <A, B, R> combine2(
        callA: suspend () -> DataResult<A>,
        callB: suspend () -> DataResult<B>,
        zip: (A, B) -> R
    ): DataResult<R> = coroutineScope {

        val a = async { callA() }
        val b = async { callB() }

        val ra = a.await()
        val rb = b.await()

        if (ra is DataResult.Error) return@coroutineScope ra
        if (rb is DataResult.Error) return@coroutineScope rb

        DataResult.Success(zip((ra as DataResult.Success).data, (rb as DataResult.Success).data))
    }

    protected suspend fun <A, B, C, R> combine3(
        callA: suspend () -> DataResult<A>,
        callB: suspend () -> DataResult<B>,
        callC: suspend () -> DataResult<C>,
        zip: (A, B, C) -> R
    ): DataResult<R> = coroutineScope {

        val a = async { callA() }
        val b = async { callB() }
        val c = async { callC() }

        val ra = a.await()
        val rb = b.await()
        val rc = c.await()

        if (ra is DataResult.Error) return@coroutineScope ra
        if (rb is DataResult.Error) return@coroutineScope rb
        if (rc is DataResult.Error) return@coroutineScope rc

        DataResult.Success(
            zip(
                (ra as DataResult.Success).data,
                (rb as DataResult.Success).data,
                (rc as DataResult.Success).data
            )
        )
    }

    protected suspend fun <T, R> combineAll(
        vararg calls: suspend () -> DataResult<T>,
        transform: (List<T>) -> R
    ): DataResult<R> = coroutineScope {

        val results = calls.map { async { it() } }.awaitAll()

        val error = results.firstOrNull { it is DataResult.Error }
        if (error is DataResult.Error) return@coroutineScope error

        DataResult.Success(transform(results.map { (it as DataResult.Success).data }))
    }


    protected suspend fun <A, B, R> chain(
        first: suspend () -> DataResult<A>,
        second: suspend (A) -> DataResult<B>,
        zip: (A, B) -> R
    ): DataResult<R> {
        return when (val a = first()) {
            is DataResult.Error -> a
            is DataResult.Success -> {
                when (val b = second(a.data)) {
                    is DataResult.Error -> b
                    is DataResult.Success -> DataResult.Success(zip(a.data, b.data))
                }
            }
        }
    }


    protected suspend fun <A, B, R> combine2Partial(
        callA: suspend () -> DataResult<A>,
        callB: suspend () -> DataResult<B>,
        zip: (A?, B?) -> R
    ): DataResult<R> = coroutineScope {

        val a = async { runCatching { callA() }.getOrNull() }
        val b = async { runCatching { callB() }.getOrNull() }

        val ra = a.await()
        val rb = b.await()

        DataResult.Success(
            zip(
                (ra as? DataResult.Success)?.data,
                (rb as? DataResult.Success)?.data
            )
        )
    }


}