package com.luanxh.shared.mvi.domain.repository

import com.example.shared.mvi.domain.data.DataResult
import com.example.shared.mvi.domain.mapper.DtoMapper


/***
 * @author 栾桂明
 * @see 对接口请求返回的数据做第一次的转化 奖返回的dto数据通过
 * mapper映射成业务的domain
 */
abstract class BaseRepository {
    protected suspend fun <DTO, DOMAIN> apiCall(
        mapper: DtoMapper<DTO, DOMAIN>,
        block: suspend () -> DataResult<DTO>
    ): DataResult<DOMAIN> {
        return when (val result = block()) {
            is DataResult.Success -> {
                try {
                    val domain = mapper.mapToDomain(result.data)
                    DataResult.Success(domain)
                } catch (e: Exception) {
                    DataResult.Error(null, "数据转换异常", e)
                }
            }

            is DataResult.Error -> result
        }
    }


    protected suspend fun <DTO, DOMAIN> apiCallList(
        mapper: DtoMapper<DTO, DOMAIN>,
        block: suspend () -> DataResult<List<DTO>>
    ): DataResult<List<DOMAIN>> = when (val result = block()) {

        is DataResult.Success -> {
            DataResult.Success(mapper.mapToDomainList(result.data))
        }

        is DataResult.Error -> result
    }

}