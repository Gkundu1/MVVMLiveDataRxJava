package com.example.mvvmexample.data.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("isSuccessful")
    @Expose
    var isSuccessful: Boolean,
    @SerializedName("message")
    @Expose
    var message: String,
    @SerializedName("user")
    @Expose
    var user: User
)