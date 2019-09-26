package com.example.mvvmexample.ui.auth

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import com.example.mvvmexample.appl.MyApp
import com.example.mvvmexample.data.network.RetroService
import com.example.mvvmexample.data.respositories.UserRepository
import com.example.mvvmexample.utils.Const

class AuthViewModel(application: Application):AndroidViewModel(application)
{
    var email: String? = null
    var password: String? = null

    var authListener: AuthListener? = null
    var repository=getApplication<MyApp>().getUserRepository()
    var context=getApplication<MyApp>().applicationContext

    fun onLoginButtonClicked(view: View){
        authListener?.onStarted()
        if(email.isNullOrEmpty() || password.isNullOrEmpty()){
            authListener?.onFailure("Invalid email or password")
            return
        }
        val loginResponse=repository.userLogin(email!!,password!!)

        /*val apiService=RetroService(context, Const.defaultRequest.add("UserName", email!!)
            .add("UserPassword", "QXN0cmFAMTIz").build())

        val loginResponse=UserRepository().dmsUserLogin(apiService)*/

        authListener?.onSuccess(loginResponse)
    }

    override fun onCleared() {
        super.onCleared()
    }
}