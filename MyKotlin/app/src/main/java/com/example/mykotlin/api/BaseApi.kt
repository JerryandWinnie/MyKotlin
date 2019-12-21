package com.example.mykotlin.api

import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.*
import java.util.*
import kotlin.collections.HashMap

/**
 * @ClassName:      BaseApi
 * @Description:     接口
 * @Author:         张伟成
 * @CreateDate:     2019/12/20 10:29
 * @UpdateDate:     2019/12/20 10:29
 */
interface BaseApi {

    @GET
    fun get(@Url url : String, @QueryMap map : HashMap<String,String>) : Observable<ResponseBody>

    @GET
    fun get(@Url url : String, @HeaderMap header : HashMap<String,String>, @QueryMap map : HashMap<String,String>) : Observable<ResponseBody>

    @FormUrlEncoded
    @POST
    fun post(@Url url : String, @FieldMap map : HashMap<String,String>) : Observable<ResponseBody>

    @FormUrlEncoded
    @POST
    fun post(@Url url : String, @HeaderMap header : HashMap<String,String>, @FieldMap map : HashMap<String,String>) : Observable<ResponseBody>
}