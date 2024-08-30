package com.pers.libs.net

import retrofit2.http.*
import java.util.*


interface ApiService {

    @GET("{path}")
    suspend fun <T> get(
        @Path("path") urlPath: String,
        @Body bodyMap: HashMap<String, Any>,
        @HeaderMap headerMap: HashMap<String, Any>
    ): ResponseBean<T>


    @POST("{path}")
    suspend fun <T> post(
        @Path("path") urlPath: String,
        @Body bodyMap: HashMap<String, Any>,
        @HeaderMap headerMap: HashMap<String, Any>
    ): ResponseBean<T>

    @PUT
    suspend fun <T> put(
        @Url urlPath: String,
        @Body bodyMap: HashMap<String, Any>,
        @HeaderMap headerMap: HashMap<String, Any>
    ): ResponseBean<T>


    @DELETE
    suspend fun <T> delete(
        @Url urlPath: String,
        @QueryMap params: HashMap<String, Any>,
        @HeaderMap header: HashMap<String, Any>
    ): ResponseBean<T>
    

}