package com.example.mvvmexample.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.mvvmexample.R
import com.example.mvvmexample.databinding.ActivityLoginBinding
import com.example.mvvmexample.utils.hide
import com.example.mvvmexample.utils.show
import com.example.mvvmexample.utils.toast
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), AuthListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel: AuthViewModel = ViewModelProviders.of(this).get(AuthViewModel::class.java)
        val binding: ActivityLoginBinding =DataBindingUtil.setContentView(this, R.layout.activity_login)
        //binding.setVariable(BR.authmodel,viewModel)
        binding.authmodel=viewModel

        viewModel.authListener=this
    }

    override fun onStarted() {
       progress_bar.show()
    }

    override fun onSuccess(loginResponse: LiveData<String>) {

       loginResponse.observe(this, Observer {
           progress_bar.hide()
           toast(it)
       })
    }

    override fun onFailure(message: String) {
        progress_bar.hide()
        toast(message)
    }
}
