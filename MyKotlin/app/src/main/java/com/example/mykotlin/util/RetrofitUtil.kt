package com.example.mykotlin.util

import com.example.mykotlin.api.BaseApi
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import java.util.concurrent.TimeUnit
import kotlin.collections.HashMap

/**
 * @ClassName:      RetrofitUtil
 * @Description:     工具类
 * @Author:         张伟成
 * @CreateDate:     2019/12/20 10:38
 * @UpdateDate:     2019/12/20 10:38
 */
class RetrofitUtil private constructor(){
    var baseApi : BaseApi? = null

    //kotlin单例的写法
    companion object {
        val instance : RetrofitUtil by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED){
            RetrofitUtil()
        }
    }

    //init 表示构造
    init {

        val interceptor = HttpLoggingInterceptor()

        val client : OkHttpClient = OkHttpClient.Builder()
            .connectTimeout(10,TimeUnit.SECONDS)
            .readTimeout(10,TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .build()

        val retrofit : Retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl("https://mobile.bwstudent.com/")
            .client(client)
            .build()

        baseApi = retrofit.create(BaseApi::class.java)
    }

    fun doGet(url: String, map: HashMap<String,String>, listener: onResponseListener){
        baseApi!!.get(url,map)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getObserver(listener))
    }
    fun doGet(url: String, headMap: HashMap<String,String>, map: HashMap<String,String>, listener: onResponseListener){
        baseApi!!.get(url,headMap,map)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getObserver(listener))
    }

    fun doPost(url: String, map: HashMap<String,String>, listener: onResponseListener){
        baseApi!!.post(url,map)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getObserver(listener))
    }
    fun doPost(url: String, headMap: HashMap<String,String>, map: HashMap<String,String>, listener: onResponseListener){
        baseApi!!.post(url,headMap,map)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getObserver(listener))
    }




    private fun getObserver(listener: onResponseListener?): Observer<ResponseBody>{
        return object : Observer<ResponseBody>{
            override fun onSubscribe(d: Disposable) {
            }

            override fun onComplete() {
            }

            override fun onNext(t: ResponseBody) {
                try {
                    val data = t.string()
                    listener!!.onSuccess(data)
                }catch (e:Exception){
                    e.printStackTrace()
                    listener!!.onFail(e.toString())
                }
            }

            override fun onError(e: Throwable) {
                listener!!.onFail(e.toString())
            }

        }
    }

    open interface onResponseListener{
        fun onSuccess(string: String)
        fun onFail(string: String)
    }

}