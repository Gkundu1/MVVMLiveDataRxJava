package com.example.mvvmexample.ui.auth

import androidx.lifecycle.LiveData
import com.example.mvvmexample.data.response.DMSLoginResponse
import com.example.mvvmexample.data.response.MVVMLoginResponse

interface AuthListener
{
    fun onStarted()
    fun onSuccess(loginResponse: LiveData<MVVMLoginResponse>)
    fun onDMSSuccess(loginResponse: LiveData<DMSLoginResponse>)
    fun onFailure(message: String)
}