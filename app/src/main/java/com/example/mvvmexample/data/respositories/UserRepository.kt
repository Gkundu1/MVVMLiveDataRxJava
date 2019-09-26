package com.example.mvvmexample.data.respositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mvvmexample.data.network.RetroService
import com.example.mvvmexample.data.response.LoginResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class UserRepository(retroService: RetroService)
{
    private var apiService:RetroService?=null

    private var mCompositeDiposable:CompositeDisposable?=null

    init {
        this.apiService=retroService
        mCompositeDiposable= CompositeDisposable()
    }

    fun userLogin(email:String,password:String):LiveData<String>
    {
        val loginresponse=MutableLiveData<String>()

        val responseObservable=apiService?.getPreparedObservable(
            apiService?.networkService?.userLogin(email,password)!!,
            LoginResponse::class.java,false,false)

        responseObservable
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribeOn(Schedulers.io())
            ?.subscribe({
                val response=it as LoginResponse
                if(response.isSuccessful)
                {
                    loginresponse.value=response.message
                }

            },{
                loginresponse.value=it.message
                /*if((it as HttpException).code()==HttpsURLConnection.HTTP_UNAUTHORIZED)
                {
                    loginresponse.value= it.response().message()
                }
                else
                {
                    loginresponse.value= it.response().message()
                }*/

            })?.let { mCompositeDiposable?.add(it) }

        return loginresponse
    }
}