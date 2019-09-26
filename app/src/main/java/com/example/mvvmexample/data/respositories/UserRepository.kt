package com.example.mvvmexample.data.respositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mvvmexample.data.network.RetroService
import com.example.mvvmexample.data.response.DMSLoginResponse
import com.example.mvvmexample.data.response.DriverData
import com.example.mvvmexample.data.response.MVVMLoginResponse
import com.example.mvvmexample.data.response.User
import com.example.mvvmexample.utils.NoInternetException
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import java.util.concurrent.CopyOnWriteArrayList
import javax.net.ssl.HttpsURLConnection

class UserRepository()
{
    constructor(retroService: RetroService) : this() {
        this.apiService=retroService
    }

    private var apiService:RetroService?=null

    private var mCompositeDiposable:CompositeDisposable?=null

    init {
        mCompositeDiposable= CompositeDisposable()
    }

    fun userLogin(email:String,password:String):LiveData<MVVMLoginResponse>
    {
        val loginresponse=MutableLiveData<MVVMLoginResponse>()

        val responseObservable=apiService?.getPreparedObservable(
            apiService?.networkService?.userLogin(email,password)!!,
            MVVMLoginResponse::class.java,false,false)

        responseObservable
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribeOn(Schedulers.io())
            ?.subscribe({
                val response=it as MVVMLoginResponse
                if(response.isSuccessful)
                {
                    loginresponse.value=response
                }

            },{
                if(it is NoInternetException)
                {
                    loginresponse.value=MVVMLoginResponse(false,it.message!!,
                        User(0,"","",null,"",""))
                }
                else
                {
                    if((it as HttpException).code()==HttpsURLConnection.HTTP_UNAUTHORIZED)
                    {
                        loginresponse.value=MVVMLoginResponse(false,"Invalid email or password",
                            User(0,"","",null,"",""))
                    }
                    else
                    {
                        loginresponse.value=MVVMLoginResponse(false,"API ERROR",
                            User(0,"","",null,"",""))
                    }

                }

            })?.let { mCompositeDiposable?.add(it) }

        return loginresponse
    }

    fun dmsUserLogin(retroService: RetroService):LiveData<DMSLoginResponse>
    {
        val loginresponse=MutableLiveData<DMSLoginResponse>()

        val responseObservable= retroService.getPreparedObservable(
            retroService.networkService.doLogin(),DMSLoginResponse::class.java,false,false)

        responseObservable.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({
                loginresponse.value=it as DMSLoginResponse

            },{
                if(it is NoInternetException)
                {
                    loginresponse.value=
                        DMSLoginResponse(CopyOnWriteArrayList<DriverData>(),"failed",503
                            ,it.message,null,null,null)
                }
                else
                {
                    loginresponse.value= DMSLoginResponse(CopyOnWriteArrayList<DriverData>(),"failed",500
                        ,"API REQUEST FAILED",null,null,null)
                }


            })?.let { mCompositeDiposable?.add(it) }

        return loginresponse
    }
}