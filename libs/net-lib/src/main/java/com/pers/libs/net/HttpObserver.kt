package com.pers.libs.net

import com.pers.libs.net.interceptor.TimeoutIOException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

object HttpObserver {

    suspend inline fun <reified T> post(
        url: String,
        params: HashMap<String, Any>,
        crossinline onSuccess: (T?) -> Unit = {},
        crossinline onFail: (errorCode: Int?, errorMsg: String?) -> Unit,
        crossinline onTimeout: (Throwable) -> Unit = {},
        crossinline onError: (Throwable) -> Unit = {},
    ): T? = coroutineScope {
        request(url, params, REQUEST.POST, onSuccess, onFail, onTimeout, onError)
    }


    suspend inline fun <reified T> get(
        url: String,
        params: HashMap<String, Any>,
        crossinline onSuccess: (T?) -> Unit = {},
        crossinline onFail: (errorCode: Int?, errorMsg: String?) -> Unit,
        crossinline onTimeout: (Throwable) -> Unit = {},
        crossinline onError: (Throwable) -> Unit = {},
    ): T? = coroutineScope {
        request(url, params, REQUEST.GET, onSuccess, onFail, onTimeout, onError)
    }

    /**
     * 开启一个新的协程，不阻塞外部的协程块
     */
    inline fun <reified T> postAsync(
        url: String,
        params: HashMap<String, Any>,
        crossinline onSuccess: (T?) -> Unit,
        crossinline onFail: (errorCode: Int?, errorMsg: String?) -> Unit,
        crossinline onTimeout: (Throwable) -> Unit = {},
        crossinline onError: (Throwable) -> Unit = {},
    ) {
        val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
        coroutineScope.launch {
            request(url, params, REQUEST.POST, onSuccess, onFail, onTimeout, onError)
            coroutineScope.cancel()
        }
    }


    inline fun <reified T> getAsync(
        url: String,
        params: HashMap<String, Any>,
        crossinline onSuccess: (T?) -> Unit,
        crossinline onFail: (errorCode: Int?, errorMsg: String?) -> Unit,
        crossinline onTimeout: (Throwable) -> Unit = {},
        crossinline onError: (Throwable) -> Unit = {},
    ) {
        val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
        coroutineScope.launch {
            request(url, params, REQUEST.GET, onSuccess, onFail, onTimeout, onError)
            coroutineScope.cancel()
        }
    }

    suspend inline fun <reified T> request(
        url: String,
        params: HashMap<String, Any>,
        requestType: REQUEST,
        crossinline onSuccess: (T?) -> Unit,
        crossinline onFail: (errorCode: Int?, errorMsg: String?) -> Unit,
        crossinline onTimeout: (Throwable) -> Unit,
        crossinline onError: (Throwable) -> Unit,
    ): T? = coroutineScope {
        var responseData: T? = null;
        val fetchDataFlow: Flow<Result<ResponseBean<T>>> = fetchData(url, params, requestType)
        fetchDataFlow.collect { result ->
            if (result.isSuccess) {
                val data: ResponseBean<T>? = result.getOrNull()
                data?.let {

                    if (it.succeed) {
                        responseData = it.data
                        onSuccess(it.data)
                    } else {
                        onFail(it.errorCode, it.errorMsg)
                    }
                }
            } else {
                val error = result.exceptionOrNull()
                if (error != null) {
                    if (error is TimeoutIOException) {
                        onTimeout(error)
                    } else {
                        onError(error)
                    }
                }
            }
        }
        responseData
    }


    inline fun <reified T> fetchData(
        urlPath: String, params: HashMap<String, Any>, requestType: REQUEST
    ): Flow<Result<ResponseBean<T>>> = flow {
        val response: ResponseBean<T> = createRequest(urlPath, params, requestType)
        if (response.data is List<*>) {
            val listType: Type = object : ParameterizedType {
                override fun getActualTypeArguments(): Array<Type> {
                    return arrayOf(T::class.java)
                }

                override fun getRawType(): Type {
                    return MutableList::class.java
                }

                override fun getOwnerType(): Type? {
                    return null
                }
            }
        } else if (response.data is Map<*, *>) {
            val objectType: Type = object : ParameterizedType {
                override fun getActualTypeArguments(): Array<Type> {
                    return arrayOf()
                }

                override fun getRawType(): Type {
                    return T::class.java
                }

                override fun getOwnerType(): Type? {
                    return null
                }
            }
        }
        emit(Result.success(response))
    }.flowOn(Dispatchers.IO).catch { exception ->
        emit(Result.failure(exception))
    }.flowOn(Dispatchers.IO)


    suspend fun <T> createRequest(
        urlPath: String, params: HashMap<String, Any>, requestType: REQUEST
    ): ResponseBean<T> {
        val bodyParams: HashMap<String, Any> = addBaseBodyParam(HashMap())
        val headerParams: HashMap<String, Any> = addBaseHeaderParam(HashMap())
        bodyParams.putAll(params)
        return when (requestType) {
            REQUEST.POST -> RetrofitManager.getInstance().create(ApiService::class.java).post(urlPath, params, headerParams)
            REQUEST.GET -> RetrofitManager.getInstance().create(ApiService::class.java).get(urlPath, params, headerParams)
            REQUEST.PUT -> RetrofitManager.getInstance().create(ApiService::class.java).put(urlPath, params, headerParams)
            REQUEST.DELETE -> RetrofitManager.getInstance().create(ApiService::class.java).delete(urlPath, params, headerParams)
        }
    }


    enum class REQUEST {
        POST, GET, PUT, DELETE
    }

    /**
     * Body的通用参数
     */
    private fun addBaseBodyParam(params: HashMap<String, Any>): HashMap<String, Any> {

        return params
    }

    /**
     * Header的通用参数
     */
    private fun addBaseHeaderParam(params: HashMap<String, Any>): HashMap<String, Any> {

        return params
    }

}