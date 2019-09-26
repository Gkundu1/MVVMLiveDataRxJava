package com.example.mvvmexample.data.network

import android.content.Context
import com.example.mvvmexample.utils.Const
import com.example.mvvmexample.utils.HelpersJava
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetroService(context: Context)
{
    var networkService: ServiceAPI

    private var apiObservables: androidx.collection.LruCache<Class<*>, Observable<*>>

    init
    {
        val baseUrl = Const.BASEURL
        apiObservables = androidx.collection.LruCache<Class<*>, Observable<*>>(10)
        val okHttpClient = buildClient(context)
        val retrofitClient=Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        networkService=retrofitClient.create(ServiceAPI::class.java)
    }

    private fun buildClient(context: Context): OkHttpClient {

        if(apiObservables!=null)
        {
            clearCache()
        }

        val okHttpClient= HelpersJava.getUnsafeOkHttpBuilderClient()

        val builder=okHttpClient
            .connectTimeout(6000, TimeUnit.MILLISECONDS)
            .readTimeout(3, TimeUnit.MINUTES)
            .writeTimeout(3, TimeUnit.MINUTES)
        builder.addInterceptor(NetworkConnectionInterceptor(context))
        return builder.build()
    }

    fun getPreparedObservable(unPreparedObservable: Observable<*>, clazz: Class<*>, cacheObservable: Boolean, useCache: Boolean): Observable<*> {

        var preparedObservable: Observable<*>? = null

        if (useCache)
        //this way we don't reset anything in the cache if this is the only instance of us not wanting to use it.
            preparedObservable = apiObservables.get(clazz)

        if (preparedObservable != null)
            return preparedObservable

        //we are here because we have never created this observable before or we didn't want to use the cache...

        preparedObservable = unPreparedObservable.subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())

        if (cacheObservable) {
            preparedObservable = preparedObservable!!.cache()
            apiObservables.put(clazz, preparedObservable!!)
        }

        return preparedObservable
    }

    fun clearCache() {
        apiObservables.evictAll()
    }


}