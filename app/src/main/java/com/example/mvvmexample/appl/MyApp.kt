package com.example.mvvmexample.appl

import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.example.mvvmexample.data.network.RetroService
import com.example.mvvmexample.data.respositories.UserRepository

class MyApp:MultiDexApplication()
{
    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
    }

    fun getUserRepository():UserRepository= UserRepository(RetroService(this.applicationContext))
}