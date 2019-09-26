package com.example.mvvmexample.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.mvvmexample.R
import com.example.mvvmexample.data.response.DMSLoginResponse
import com.example.mvvmexample.data.response.MVVMLoginResponse
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

    override fun onSuccess(loginResponse: LiveData<MVVMLoginResponse>) {

        loginResponse.observe(this, Observer {
            progress_bar.hide()
            if(it.isSuccessful)
            {
                toast(it.user.name)
            }
            else
            {
                toast(it.message)
            }
        })
    }

    override fun onDMSSuccess(loginResponse: LiveData<DMSLoginResponse>) {
        loginResponse.observe(this, Observer {
            progress_bar.hide()
            it?.statusMessage?.let { it1 -> toast(it1) }
        })
    }

    override fun onFailure(message: String) {
        progress_bar.hide()
        toast(message)
    }
}
