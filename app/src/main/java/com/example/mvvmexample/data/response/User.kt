package com.example.mvvmexample.data.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class User(@SerializedName("id")
                @Expose
                var  id:Int,
@SerializedName("name")
@Expose
var  name:String,
@SerializedName("email")
@Expose
var  email:String,
@SerializedName("email_verified_at")
@Expose
var emailVerifiedAt:Any?,
@SerializedName("created_at")
@Expose
var  createdAt:String,
@SerializedName("updated_at")
@Expose
var  updatedAt:String)
