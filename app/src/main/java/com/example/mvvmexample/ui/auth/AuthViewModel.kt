package com.example.mvvmexample.ui.auth

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import com.example.mvvmexample.appl.MyApp

class AuthViewModel(application: Application):AndroidViewModel(application)
{
    var email: String? = null
    var password: String? = null

    var authListener: AuthListener? = null
    var repository=getApplication<MyApp>().getUserRepository()

    fun onLoginButtonClicked(view: View){
        authListener?.onStarted()
        if(email.isNullOrEmpty() || password.isNullOrEmpty()){
            authListener?.onFailure("Invalid email or password")
            return
        }
        val loginResponse=repository.userLogin(email!!,password!!)

        authListener?.onSuccess(loginResponse)
    }

    override fun onCleared() {
        super.onCleared()
    }
}